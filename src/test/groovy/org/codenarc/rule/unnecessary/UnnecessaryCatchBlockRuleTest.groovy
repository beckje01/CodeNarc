/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryCatchBlockRule
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 329 $ - $Date: 2010-04-29 04:20:25 +0200 (Thu, 29 Apr 2010) $
 */
class UnnecessaryCatchBlockRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == "UnnecessaryCatchBlock"
    }

    void testSuccessScenario() {
        final SOURCE = '''
        	try {
                foo()
            } catch (Exception e) {
                throw new RuntimeException(e)
            }
        	try {
                foo()
            } catch (IOException e1) {
                throw e1
            } catch (Exception e2) {
                throw new RuntimeException(e2)
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testSingleCatchBlock() {
        final SOURCE = '''
        	try {
                foo()
            } catch (Exception e) {
                throw e
            }
        '''
        assertSingleViolation(SOURCE, 4, 'catch (Exception e)', 'Catch statement can probably be removed')
    }

    void testMultipleCatchBlocks() {
        final SOURCE = '''
        	try {
                foo()
            } catch (IOException e1) {
                throw e1
            } catch (Exception e2) {
                throw e2
            }
        '''
        assertTwoViolations(SOURCE,
                4, 'catch (IOException e1)',
                6, 'catch (Exception e2)')   
    }

    protected Rule createRule() {
        new UnnecessaryCatchBlockRule()
    }
}