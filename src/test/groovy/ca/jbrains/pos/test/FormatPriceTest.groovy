package ca.jbrains.pos.test

import ca.jbrains.pos.Price
import spock.lang.Specification
import spock.lang.Unroll


class FormatPriceTest extends Specification {
    @Unroll("Format €#price.euro()")
    def "format price"() {
        expect:
        text == new WriterDisplay(null).formatPrice(price)

        where:
        price                 | text
        Price.cents(198)      | "EUR 1.98"
        Price.cents(0)        | "EUR 0.00"
        Price.cents(1)        | "EUR 0.01"
        Price.cents(70)       | "EUR 0.70"
        Price.cents(300)      | "EUR 3.00"
        Price.cents(12873612) | "EUR 128736.12"
    }

}