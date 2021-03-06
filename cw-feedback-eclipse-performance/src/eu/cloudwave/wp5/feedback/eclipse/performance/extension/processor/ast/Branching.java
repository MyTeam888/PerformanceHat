package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A Branching represents every java construct which chooses one branch to execute based on a condition.
 * Branching includes: ifs, switches and conditional expressions
 * @author Markus Knecht
 *
 */
public class Branching extends AAstNode<org.eclipse.jdt.core.dom.ASTNode>{
	
	  //The three possible backing nodes
	  private org.eclipse.jdt.core.dom.IfStatement ifStat = null;
	  private org.eclipse.jdt.core.dom.ConditionalExpression condStat = null;
	  private org.eclipse.jdt.core.dom.SwitchStatement switchStat = null;

	  //Lazy calced SubNodes
	  private List<IAstNode> branches = null;
	  private IAstNode condition = null;

	  Branching(org.eclipse.jdt.core.dom.IfStatement ifStat, AstContext ctx) {
		 super(ifStat,ctx);
		 this.ifStat = ifStat;
      }
 	  
 	  Branching(org.eclipse.jdt.core.dom.ConditionalExpression condStat, AstContext ctx) {
		 super(condStat,ctx);
		 this.condStat = condStat;
      }
 	  
 	  Branching(org.eclipse.jdt.core.dom.SwitchStatement switchStat, AstContext ctx) {
 		 super(switchStat,ctx);
 		 this.switchStat = switchStat;
       }
 	  
 	 /**
 	  * returns all the possible branches
 	  * @return IAstNode representing the Branch
 	  */
 	  public List<IAstNode> getBranches(){
 		  if(branches == null){
 			  branches = Lists.newArrayList();
 			  if(ifStat != null){
 				  org.eclipse.jdt.core.dom.Statement sm = ifStat.getThenStatement();
 				  if(sm != null) branches.add(StaticAstFactory.fromEclipseAstNodeOrDefault(sm, ctx));
 				  sm = ifStat.getElseStatement();
 				  if(sm != null) branches.add(StaticAstFactory.fromEclipseAstNodeOrDefault(sm, ctx));
 			  } else if (condStat != null) {
 				  org.eclipse.jdt.core.dom.Expression exp = condStat.getThenExpression();
 				  if(exp != null) branches.add(StaticAstFactory.fromEclipseAstNodeOrDefault(exp, ctx));
 				  exp = condStat.getElseExpression();
 				  if(exp != null) branches.add(StaticAstFactory.fromEclipseAstNodeOrDefault(exp, ctx));
 			  } else if (switchStat != null){
 				  for(Object st:switchStat.statements()){
 					  branches.add(StaticAstFactory.fromEclipseAstNodeOrDefault((ASTNode)st, ctx));
 				  }
 			  }
 		  }
 		  		 
 		  return branches;
 	  }
 	  
 	  /**
 	   * Gets the Condition node which defines which bloc is taken
 	   * @return IAstNode representing the condition
 	   */
 	  public IAstNode getCondition(){
 		  if(condition == null){
 			  if(ifStat != null){
 				 condition = StaticAstFactory.fromEclipseAstNodeOrDefault(ifStat.getExpression(), ctx);
 			  } else if (condStat != null) {
 				 condition = StaticAstFactory.fromEclipseAstNodeOrDefault(condStat.getExpression(), ctx);
 			  } else if (switchStat != null){
 				 condition = StaticAstFactory.fromEclipseAstNodeOrDefault(switchStat.getExpression(), ctx);
 			  }	  
 		  }
 		
 		 return condition;
 	  }
 	  
 	  /**
 	   * Checks if it is possible that none of the branches are executed
 	   * @return true if no branch may be executed and false if always a branch is executed
 	   */
 	  public boolean isSkippable(){
 		 if(ifStat != null){
  			return ifStat.getElseStatement() == null;
  		 } else if (condStat != null) {
  			return false;
  		 } else if (switchStat != null){
   			return false;
  		 }
 		 return false;
 	  }
	

}
