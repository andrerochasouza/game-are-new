module sormula {
    requires java.logging;
    requires java.naming;

    requires transitive java.sql;

    exports org.sormula;
    exports org.sormula.active;
    exports org.sormula.active.operation;
    exports org.sormula.annotation;
    exports org.sormula.annotation.cache;
    exports org.sormula.annotation.cascade;
    exports org.sormula.cache;
    exports org.sormula.cache.readonly;
    exports org.sormula.cache.readwrite;
    exports org.sormula.cache.writable;
    exports org.sormula.log;
    exports org.sormula.operation;
    exports org.sormula.operation.aggregate;
    exports org.sormula.operation.cascade;
    exports org.sormula.operation.cascade.lazy;
    exports org.sormula.operation.monitor;
    exports org.sormula.reflect;
    exports org.sormula.selector;
    exports org.sormula.translator;
    exports org.sormula.translator.standard;

}
