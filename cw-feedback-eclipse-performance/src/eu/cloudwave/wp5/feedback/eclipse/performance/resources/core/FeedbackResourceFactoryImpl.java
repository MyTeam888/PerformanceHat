/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.performance.resources.core;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.AbstractFeedbackResourceFactoryImpl;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceExtensionFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Implementation of {@link FeedbackResourceFactory}.
 */
public class FeedbackResourceFactoryImpl extends AbstractFeedbackResourceFactoryImpl implements FeedbackResourceFactory {

  public FeedbackResourceFactoryImpl() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackResourceExtensionFactory getFeedbackResourceExtensionFactory() {
    return PerformancePluginActivator.instance(FeedbackResourceExtensionFactory.class);
  };

  /**
   * {@inheritDoc}
   */
  @Override
  protected FeedbackResourceFactory getFeedbackResourceFactory() {
    return PerformancePluginActivator.instance(FeedbackResourceFactory.class);
  };
}
