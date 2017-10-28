package me.bramhaag.annotationtest.printer;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExportsTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ModuleTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.OpensTree;
import com.sun.source.tree.PackageTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ProvidesTree;
import com.sun.source.tree.RequiresTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.UsesTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;

public abstract class TreeVisitor8<R, P> implements TreeVisitor<R, P> {

    public abstract R visit(Tree tree, P p);

    public R visit(Tree tree) {
        return visit(tree, null);
    }

    public abstract R visit(Iterable<? extends Tree> trees, P p);

    public R visit(Iterable<? extends Tree> tree) {
        return visit(tree, null);
    }

    @Override
    public R visitAnnotatedType(AnnotatedTypeTree annotatedTypeTree, P p) {
        visit(annotatedTypeTree.getAnnotations(), p);
        visit(annotatedTypeTree.getUnderlyingType(), p);
        return null;
    }

    @Override
    public R visitAnnotation(AnnotationTree annotationTree, P p) {
        visit(annotationTree.getAnnotationType(), p);
        visit(annotationTree.getArguments(), p);
        return null;
    }

    @Override
    public R visitMethodInvocation(MethodInvocationTree methodInvocationTree, P p) {
        visit(methodInvocationTree.getMethodSelect(), p);
        visit(methodInvocationTree.getTypeArguments(), p);
        visit(methodInvocationTree.getArguments(), p);
        return null;
    }

    @Override
    public R visitAssert(AssertTree assertTree, P p) {
        visit(assertTree.getCondition(), p);
        visit(assertTree.getDetail(), p);
        return null;
    }

    @Override
    public R visitAssignment(AssignmentTree assignmentTree, P p) {
        visit(assignmentTree.getExpression(), p);
        visit(assignmentTree.getVariable());
        return null;
    }

    @Override
    public R visitCompoundAssignment(CompoundAssignmentTree compoundAssignmentTree, P p) {
        visit(compoundAssignmentTree.getExpression());
        visit(compoundAssignmentTree.getVariable());
        return null;
    }

    @Override
    public R visitBinary(BinaryTree binaryTree, P p) {
        visit(binaryTree.getLeftOperand());
        visit(binaryTree.getRightOperand());
        return null;
    }

    @Override
    public R visitBlock(BlockTree blockTree, P p) {
        visit(blockTree.getStatements());
        return null;
    }

    @Override
    public R visitBreak(BreakTree breakTree, P p) {
        return null;
    }

    @Override
    public R visitCase(CaseTree caseTree, P p) {
        visit(caseTree.getExpression());
        visit(caseTree.getStatements());
        return null;
    }

    @Override
    public R visitCatch(CatchTree catchTree, P p) {
        visit(catchTree.getBlock());
        visit(catchTree.getParameter());
        return null;
    }

    @Override
    public R visitClass(ClassTree classTree, P p) {
        visit(classTree.getExtendsClause());
        visit(classTree.getImplementsClause());
        visit(classTree.getMembers());
        visit(classTree.getModifiers());
        visit(classTree.getTypeParameters());
        return null;
    }

    @Override
    public R visitConditionalExpression(ConditionalExpressionTree conditionalExpressionTree, P p) {
        visit(conditionalExpressionTree.getCondition());
        visit(conditionalExpressionTree.getTrueExpression());
        visit(conditionalExpressionTree.getFalseExpression());
        return null;
    }

    @Override
    public R visitContinue(ContinueTree continueTree, P p) {
        return null;
    }

    @Override
    public R visitDoWhileLoop(DoWhileLoopTree doWhileLoopTree, P p) {
        visit(doWhileLoopTree.getCondition());
        visit(doWhileLoopTree.getStatement());
        return null;
    }

    @Override
    public R visitErroneous(ErroneousTree erroneousTree, P p) {
        visit(erroneousTree.getErrorTrees());
        return null;
    }

    @Override
    public R visitExpressionStatement(ExpressionStatementTree expressionStatementTree, P p) {
        visit(expressionStatementTree.getExpression());
        return null;
    }

    @Override
    public R visitEnhancedForLoop(EnhancedForLoopTree enhancedForLoopTree, P p) {
        visit(enhancedForLoopTree.getVariable());
        visit(enhancedForLoopTree.getExpression());
        visit(enhancedForLoopTree.getStatement());
        return null;
    }

    @Override
    public R visitForLoop(ForLoopTree forLoopTree, P p) {
        visit(forLoopTree.getInitializer());
        visit(forLoopTree.getCondition());
        visit(forLoopTree.getUpdate());
        visit(forLoopTree.getStatement());
        return null;
    }

    @Override
    public R visitIdentifier(IdentifierTree identifierTree, P p) {
        return null;
    }

    @Override
    public R visitIf(IfTree ifTree, P p) {
        visit(ifTree.getCondition());
        visit(ifTree.getThenStatement());
        visit(ifTree.getElseStatement());
        return null;
    }

    @Override
    public R visitImport(ImportTree importTree, P p) {
        visit(importTree.getQualifiedIdentifier());
        return null;
    }

    @Override
    public R visitArrayAccess(ArrayAccessTree arrayAccessTree, P p) {
        visit(arrayAccessTree.getExpression());
        visit(arrayAccessTree.getIndex());
        return null;
    }

    @Override
    public R visitLabeledStatement(LabeledStatementTree labeledStatementTree, P p) {
        visit(labeledStatementTree.getStatement());
        return null;
    }

    @Override
    public R visitLiteral(LiteralTree literalTree, P p) {
        return null;
    }

    @Override
    public R visitMethod(MethodTree methodTree, P p) {
        visit(methodTree.getTypeParameters());
        visit(methodTree.getModifiers());
        visit(methodTree.getDefaultValue());
        visit(methodTree.getParameters());
        visit(methodTree.getReceiverParameter());
        visit(methodTree.getReturnType());
        visit(methodTree.getThrows());
        visit(methodTree.getThrows());
        visit(methodTree.getBody());
        return null;
    }

    @Override
    public R visitModifiers(ModifiersTree modifiersTree, P p) {
        visit(modifiersTree.getAnnotations());
        return null;
    }

    @Override
    public R visitNewArray(NewArrayTree newArrayTree, P p) {
        visit(newArrayTree.getType());
        visit(newArrayTree.getAnnotations());
        visit(newArrayTree.getDimensions());
        visit(newArrayTree.getInitializers());
        for (Iterable<? extends Tree> trees : newArrayTree.getDimAnnotations()) visit(trees);
        return null;
    }

    @Override
    public R visitNewClass(NewClassTree newClassTree, P p) {
        visit(newClassTree.getEnclosingExpression());
        visit(newClassTree.getIdentifier());
        visit(newClassTree.getTypeArguments());
        visit(newClassTree.getArguments());
        visit(newClassTree.getClassBody());
        return null;
    }

    @Override
    public R visitLambdaExpression(LambdaExpressionTree lambdaExpressionTree, P p) {
        visit(lambdaExpressionTree.getParameters());
        visit(lambdaExpressionTree.getBody());
        return null;
    }

    @Override
    public R visitParenthesized(ParenthesizedTree parenthesizedTree, P p) {
        visit(parenthesizedTree.getExpression());
        return null;
    }

    @Override
    public R visitReturn(ReturnTree returnTree, P p) {
        visit(returnTree.getExpression());
        return null;
    }

    @Override
    public R visitMemberSelect(MemberSelectTree memberSelectTree, P p) {
        visit(memberSelectTree.getExpression());
        return null;
    }

    @Override
    public R visitMemberReference(MemberReferenceTree memberReferenceTree, P p) {
        visit(memberReferenceTree.getQualifierExpression());
        return null;
    }

    @Override
    public R visitEmptyStatement(EmptyStatementTree emptyStatementTree, P p) {
        return null;
    }

    @Override
    public R visitSwitch(SwitchTree switchTree, P p) {
        visit(switchTree.getCases());
        visit(switchTree.getExpression());
        return null;
    }

    @Override
    public R visitSynchronized(SynchronizedTree synchronizedTree, P p) {
        visit(synchronizedTree.getExpression());
        visit(synchronizedTree.getBlock());
        return null;
    }

    @Override
    public R visitThrow(ThrowTree throwTree, P p) {
        visit(throwTree.getExpression());
        return null;
    }

    @Override
    public R visitCompilationUnit(CompilationUnitTree compilationUnitTree, P p) {
        visit(compilationUnitTree.getPackageAnnotations());
        visit(compilationUnitTree.getPackageName());
        visit(compilationUnitTree.getImports());
        visit(compilationUnitTree.getTypeDecls());
        return null;
    }

    @Override
    public R visitTry(TryTree tryTree, P p) {
        visit(tryTree.getResources());
        visit(tryTree.getBlock());
        visit(tryTree.getCatches());
        visit(tryTree.getFinallyBlock());
        return null;
    }

    @Override
    public R visitParameterizedType(ParameterizedTypeTree parameterizedTypeTree, P p) {
        visit(parameterizedTypeTree.getType());
        visit(parameterizedTypeTree.getTypeArguments());
        return null;
    }

    @Override
    public R visitUnionType(UnionTypeTree unionTypeTree, P p) {
        visit(unionTypeTree.getTypeAlternatives());
        return null;
    }

    @Override
    public R visitIntersectionType(IntersectionTypeTree intersectionTypeTree, P p) {
        visit(intersectionTypeTree.getBounds());
        return null;
    }

    @Override
    public R visitArrayType(ArrayTypeTree arrayTypeTree, P p) {
        visit(arrayTypeTree.getType());
        return null;
    }

    @Override
    public R visitTypeCast(TypeCastTree typeCastTree, P p) {
        visit(typeCastTree.getType());
        visit(typeCastTree.getExpression());
        return null;
    }

    @Override
    public R visitPrimitiveType(PrimitiveTypeTree primitiveTypeTree, P p) {
        return null;
    }

    @Override
    public R visitTypeParameter(TypeParameterTree typeParameterTree, P p) {
        visit(typeParameterTree.getAnnotations());
        visit(typeParameterTree.getBounds());
        return null;
    }

    @Override
    public R visitInstanceOf(InstanceOfTree instanceOfTree, P p) {
        visit(instanceOfTree.getType());
        visit(instanceOfTree.getExpression());
        return null;
    }

    @Override
    public R visitUnary(UnaryTree unaryTree, P p) {
        visit(unaryTree.getExpression());
        return null;
    }

    @Override
    public R visitVariable(VariableTree variableTree, P p) {
        visit(variableTree.getModifiers());
        visit(variableTree.getType());
        visit(variableTree.getNameExpression());
        visit(variableTree.getInitializer());
        return null;
    }

    @Override
    public R visitWhileLoop(WhileLoopTree whileLoopTree, P p) {
        visit(whileLoopTree.getCondition());
        visit(whileLoopTree.getStatement());
        return null;
    }

    @Override
    public R visitWildcard(WildcardTree wildcardTree, P p) {
        visit(wildcardTree.getBound());
        return null;
    }

    @Override
    public R visitOther(Tree tree, P p) {

        return null;
    }

    @Override
    public R visitPackage(PackageTree node, P p) {
        return null;
    }

    @Override
    public R visitModule(ModuleTree node, P p) {
        return null;
    }

    @Override
    public R visitExports(ExportsTree node, P p) {
        return null;
    }

    @Override
    public R visitOpens(OpensTree node, P p) {
        return null;
    }

    @Override
    public R visitProvides(ProvidesTree node, P p) {
        return null;
    }

    @Override
    public R visitRequires(RequiresTree node, P p) {
        return null;
    }

    @Override
    public R visitUses(UsesTree node, P p) {
        return null;
    }
}