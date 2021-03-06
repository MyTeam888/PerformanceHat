/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.advices;

/**
 * This class provides bunch of methods to get data about client invocation methods
 */
public interface InvocationDataProvider {

  /**
   * Analysis monitoring data and checks if the method has already been invoked before from the given caller. If not, we
   * have to calculate the cost impact.
   * 
   * @param invokedClassName
   * @param invokedMethodName
   * @param callerClassName
   * @param callerMethodName
   * @return yes/no
   */
  public boolean isNewlyInvoked(final String invokedClassName, final String invokedMethodName, final String callerClassName, final String callerMethodName);

}
