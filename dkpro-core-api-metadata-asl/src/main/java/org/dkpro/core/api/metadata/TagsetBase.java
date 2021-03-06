/*
 * Copyright 2017
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dkpro.core.api.metadata;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Abstract base class for {@link Tagset}s.
 */
public abstract class TagsetBase
    implements Tagset
{
    private TagsetMetaData metadata = new TagsetMetaData();
    
    public TagsetMetaData getMetadata()
    {
        return metadata;
    }

    public void setMetadata(TagsetMetaData aMetadata)
    {
        metadata = aMetadata;
    }    
    
    @Override
    public TagsetMetaData getMetaData(String aLayer, String aTagsetName)
    {
        return metadata;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        Map<String, String> layers = getLayers();

        sb.append("There are [" + layers.size() + "] layers:");

        for (Entry<String, String> e : layers.entrySet()) {
            if (sb.length() > 0) {
                sb.append('\n');
            }

            Set<String> tags = listTags(e.getKey(), e.getValue());
            sb.append("Tagset [" + e.getValue() + "] for layer [" + e.getKey() + "] contains [")
                    .append(tags.size()).append("] tags: ");
            for (String tag : tags) {
                sb.append(tag);
                sb.append(" ");
            }

        }
        return sb.toString();
    }
}
