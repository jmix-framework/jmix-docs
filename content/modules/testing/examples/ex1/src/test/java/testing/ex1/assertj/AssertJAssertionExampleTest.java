package testing.ex1.assertj;

import org.junit.jupiter.api.Test;


import java.util.List;

// tag::unit-test-assertj-assertion-example-import[]
import static org.assertj.core.api.Assertions.assertThat;
// end::unit-test-assertj-assertion-example-import[]
class AssertJAssertionExampleTest {

    @Test
    void stringAssertion() {


        // tag::unit-test-assertj-assertion-example-string-assertion[]
        // given:
        String customerName = "Mike Myers";

        // expect:
        assertThat(customerName)
                .startsWith("Mike")
                .endsWith("Myers");
        // end::unit-test-assertj-assertion-example-string-assertion[]
    }
    @Test
    void listAssertions() {

        // tag::unit-test-assertj-assertion-example-list-assertion[]
        // given:
        String bruceWillis = "Bruce Willis";
        String mikeMyers = "Mike Myers";
        String eddiMurphy = "Eddi Murphy";

        // when:
        List<String> customers = List.of(mikeMyers, eddiMurphy);

        // expect:
        assertThat(customers)
                .hasSize(2)
                .contains(eddiMurphy)
                .doesNotContain(bruceWillis);
        // end::unit-test-assertj-assertion-example-list-assertion[]
    }
}
