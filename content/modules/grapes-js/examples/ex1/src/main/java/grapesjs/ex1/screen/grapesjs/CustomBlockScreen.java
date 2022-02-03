package grapesjs.ex1.screen.grapesjs;

import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("sample_CustomBlockScreen")
@UiDescriptor("custom-block-screen.xml")
public class CustomBlockScreen extends Screen {

/*    @Autowired
    private GrapesJsHtmlEditor htmlEditor;*/

    /*@Subscribe
    protected void onInit(InitEvent event) {
        GjsBlock testBlock = new GjsBlock();
        testBlock.setName("h2-block");
        testBlock.setLabel("Heading2");
        testBlock.setCategory("Basic");
        testBlock.setContent("<![CDATA[\n" +
                "           <h2>Put your title here</h2>\n" +
                "       ]]>");
        testBlock.setAttributes("<![CDATA[\n" +
                "           {\n" +
                "              title: 'Insert h2 block',\n" +
                "              class:'fa fa-th'\n" +
                "           }\n" +
                "       ]]>");
        ArrayList<GjsBlock> list = new ArrayList<>();
        list.add(testBlock);
        htmlEditor.addBlocks(list);
        htmlEditor.getCustomBlocks();
    }*/
}