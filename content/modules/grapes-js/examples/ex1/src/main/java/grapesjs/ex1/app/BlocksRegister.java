package grapesjs.ex1.app;

import io.jmix.core.security.Authenticated;
import io.jmix.grapesjs.component.GjsBlock;
import io.jmix.grapesjs.component.GjsBlocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// tag::blocks-register[]
@Component
public class BlocksRegister {

    @Autowired
    protected GjsBlocksRepository gjsBlocksRepository;

    @Authenticated
    public void registerBlock() {
        GjsBlock testBlock = new GjsBlock(); // <1>
        testBlock.setName("h1-block");
        testBlock.setLabel("Heading");
        testBlock.setCategory("Basic");
        testBlock.setContent("<h1>Put your title here</h1>");
        testBlock.setAttributes("{" +
                "              title: 'Insert h1 block',\n" +
                "              class:'fa fa-th'\n" +
                "       }");
        gjsBlocksRepository.registerBlock(testBlock); // <2>
    }
}
// end::blocks-register[]
