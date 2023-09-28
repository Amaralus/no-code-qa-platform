package org.example.nocodetestplatform.rocksdb;

import org.springframework.data.keyvalue.core.IdentifierGenerator;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.keyvalue.core.mapping.KeyValuePersistentEntity;
import org.springframework.data.keyvalue.core.mapping.KeyValuePersistentProperty;
import org.springframework.data.mapping.context.MappingContext;

public class CustomKeyValueTemplate extends KeyValueTemplate {
    public CustomKeyValueTemplate(KeyValueAdapter adapter) {
        super(adapter);
    }

    public CustomKeyValueTemplate(KeyValueAdapter adapter, MappingContext<? extends KeyValuePersistentEntity<?, ?>, ? extends KeyValuePersistentProperty<?>> mappingContext) {
        super(adapter, mappingContext);
    }

    public CustomKeyValueTemplate(KeyValueAdapter adapter, MappingContext<? extends KeyValuePersistentEntity<?, ?>, ? extends KeyValuePersistentProperty<?>> mappingContext, IdentifierGenerator identifierGenerator) {
        super(adapter, mappingContext, identifierGenerator);
    }

    @Override
    public void destroy() {
        // preventing database cleanup
    }
}
