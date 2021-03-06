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
package org.dkpro.core.ngrams;

import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ResourceMetaData;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import eu.openminted.share.annotations.api.DocumentationResource;

/**
 * N-gram annotator.
 */
@ResourceMetaData(name = "N-Gram Annotator")
@DocumentationResource("${docbase}/component-reference.html#engine-${shortClassName}")
@TypeCapability(
    inputs = {
        "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
        "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token"},
    outputs = {
        "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.NGram"})
public class NGramAnnotator
    extends JCasAnnotator_ImplBase
{
    /**
     * The length of the n-grams to generate (the "n" in n-gram).
     */
    public static final String PARAM_N = "N";
    @ConfigurationParameter(name = PARAM_N, mandatory = true, defaultValue = "3")
    private int n;

    @Override
    public void process(JCas aJCas)
        throws AnalysisEngineProcessException
    {
        for (Sentence s : select(aJCas, Sentence.class)) {
            for (NGram ngram : NGramIterable.create(selectCovered(Token.class, s), n)) {
                ngram.addToIndexes();
            }
        }
    }
}
