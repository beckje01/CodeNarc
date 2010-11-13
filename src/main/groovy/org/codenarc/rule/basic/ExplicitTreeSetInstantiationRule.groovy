/*
 * Copyright 2009 the original author or authors.
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
package org.codenarc.rule.basic

import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule

/**
 * This rule checks for the explicit instantiation of a TreeSet. In Groovy, it is best to write "new TreeSet()" as "[] as SortedSet", which creates the same object.
 *
 * @author Hamlet D'Arcy
 * @version $Revision$ - $Date$
 */
class ExplicitTreeSetInstantiationRule extends AbstractAstVisitorRule {
    String name = 'ExplicitTreeSetInstantiation'
    int priority = 2
    Class astVisitorClass = ExplicitTreeSetInstantiationAstVisitor
}

class ExplicitTreeSetInstantiationAstVisitor extends AbstractAstVisitor {

    def void visitConstructorCallExpression(ConstructorCallExpression call) {
        if (isFirstVisit(call) && call?.type?.name == 'TreeSet') {
            addViolation call
        }
        super.visitConstructorCallExpression call
    }
}
