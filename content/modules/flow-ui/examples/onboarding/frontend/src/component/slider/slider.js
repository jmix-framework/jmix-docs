// tag::js-component[]
import 'jquery/jquery.js'
import 'jquery-ui/dist/jquery-ui.js'
import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {defineCustomElement} from '@vaadin/component-base/src/define.js';
import {ElementMixin} from '@vaadin/component-base/src/element-mixin.js';

class Slider extends ElementMixin(PolymerElement) { // <1>

    static get is() {
        return 'demo-slider'; // <2>
    }

    static get properties() { // <3>
        return {
            min: {
                type: Number,
                value: 0,
                observer: '_onMinChange' // <4>
            },

            max: {
                type: Number,
                value: 100,
                observer: '_onMaxChange'
            },

            value: {
                type: Number,
                notify: true,
                observer: '_onValueChange'
            },

            /** @private */
            _slider: {
                type: Object
            }
        }
    }

    ready() {
        super.ready();

        this._slider = $(this); // <5>
        this._slider.slider({ // <6>
            min: this.min,
            max: this.max,

            change: function (event, ui) {
                if (this.value === ui.value) {
                    return;
                }
                this.value = ui.value;
                const slideChangeEvent = new CustomEvent(
                    'custom-slide-changed',
                    {detail: {value: ui.value}}
                );
                this.dispatchEvent(slideChangeEvent); // <7>
            }
        });
    }

    /**
     * @protected
     */
    _onValueChange(value) { // <8>
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("value", value);
    }

    /**
     * @protected
     */
    _onMinChange(value) {
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("option", "min", value);
    }

    /**
     * @protected
     */
    _onMaxChange(value) {
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("option", "max", value);
    }
}

defineCustomElement(Slider); // <9>

export {Slider};
// end::js-component[]