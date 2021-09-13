package ui.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;

// tag::key-word[]
public enum KeyWord implements EnumClass<String> {
    BYTE("byte"),
    SHORT("short"),
    //...
// end::key-word[]
    INT("int"),
    LONG("long"),
    CHAR("char"),
    FLOAT("float"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    IF("if"),
    ELSE("else"),
    SWITCH("switch"),
    CASE("case"),
    DEFAULT("default"),
    WHILE("while"),
    DO("do"),
    BREAK("break"),
    CONTINUE("continue"),
    FOR("for"),
    TRY("try"),
    CATCH("catch"),
    FINALLY("finally"),
    THROW("throw"),
    THROWS("throws"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC("public"),
    IMPORT("import"),
    PACKAGE("package"),
    CLASS("class"),
    INTERFACE("interface"),
    EXTENDS("extends"),
    IMPLEMENTS("implements"),
    STATIC("static"),
    FINAL("final"),
    VOID("void"),
    ABSTRACT("abstract"),
    NATIVE("native"),
    NEW("new"),
    RETURN("return"),
    THIS("this"),
    SUPER("super"),
    SYNCHRONIZED("synchronized"),
    VOLATILE("volatile"),
    CONST("const"),
    GOTO("goto"),
    INSTANCEOF("instanceof"),
    ENUM("enum"),
    ASSERT("assert "),
    // tag::key-word[]
    STRICTFP("strictfp");

    private String id;

    KeyWord(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    // end::key-word[]


    @Nullable
    public static KeyWord fromId(String id) {
        for (KeyWord at : KeyWord.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
    // tag::key-word[]
}
// end::key-word[]
