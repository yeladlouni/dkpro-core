/*
 * Copyright 2012
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
package org.dkpro.core.textnormalizer.util;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.dkpro.core.api.frequency.provider.FrequencyCountProvider;
import org.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;
import org.dkpro.core.api.frequency.util.FrequencyDistribution;
import org.dkpro.core.api.resources.DkproContext;
import org.dkpro.core.frequency.Web1TFileAccessProvider;
import org.dkpro.core.io.web1t.util.Web1TConverter;

public class CreateNormalizerModel
{

    public static void main(String[] args)
        throws Exception
    {
        FrequencyDistribution<String> freqDist = new FrequencyDistribution<String>();

        File context = DkproContext.getContext().getWorkspace("web1t");
        FrequencyCountProvider provider = new Web1TFileAccessProvider("de",
                new File(context, "de"), 1, 1);

        Iterator<String> ngramIterator = provider.getNgramIterator(1);
        while (ngramIterator.hasNext()) {
            String unigram = ngramIterator.next();

            if (StringUtils.containsAny(unigram, new char[] { 'ä', 'ö', 'ü', })
                    || unigram.contains("ae") || unigram.contains("ae") || unigram.contains("ae")) {
                freqDist.addSample(unigram, provider.getFrequency(unigram));
            }
        }

        ConditionalFrequencyDistribution<Integer, String> cfd = 
                new ConditionalFrequencyDistribution<Integer, String>();
        cfd.setFrequencyDistribution(1, freqDist);

        Web1TConverter converter = new Web1TConverter("target/model");
        converter.setMaxNgramLength(1);
        converter.setSplitThreshold(10);
        converter.add(cfd);
        converter.createIndex();
    }
}
