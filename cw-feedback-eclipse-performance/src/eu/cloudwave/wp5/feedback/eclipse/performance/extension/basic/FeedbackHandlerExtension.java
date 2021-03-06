package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.PerformanceBuilder;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.handlers.ImpactPropagationHandler;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformanceHatExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.AstRoot;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ImplementorTagLookupHelper;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ParameterDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

/**
 * A Datasource plugin, that uses the FeedbackHandlerEclipseClient 
 * to connect to generate tags from data collected by the fdd server
 * @author Markus Knecht
 */
public class FeedbackHandlerExtension implements PerformanceHatExtension{
	 
	private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.FeedbackHandlerPlugin";
	private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	private static final String AVG_EXEC_TIME_TAG = "AvgExecutionTime";
	  
	//The fdd client to conect to the Server
	private final FeedbackHandlerEclipseClient fddClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return ID;
	}	
	
	/**
	 * {@inheritDoc}
	 */	
	public List<String> getProvidedTags(){
		return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	}
	
	 
	 /**
	  * {@inheritDoc}
	  */
	  @Override
	  public void processPerformanceAst(AstRoot ast/*, AstRoot oldAst*/) {
		  ast.accept(createPerformanceVisitor(ast.getContext()));
		  // Vom Gescheiterten Versuch einen zweiten Ast zu erhalten um differenz basierte analysen zu machen
		  //oldAst.accept(createPerformanceVisitor(oldAst.getContext()));
	  }

	  private PerformanceVisitor createPerformanceVisitor(AstContext rootContext) {
		//Creates a visitor, that attaches tags to Method Calls, Method Declaration and Constructor Invocations
		//  As well as Method parameters
		return new PerformanceVisitor() {	
			
			@Override
			public PerformanceVisitor visit(MethodDeclaration method) {
				//Find the Definition location of the Method
				MethodLocator loc = method.createCorrespondingMethodLocation();
				//get the average Excecution Time for the Method
				Double measure = fddClient.avgExecTime(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes);
				//get the collection Size of the return value
				Double averageSize = fddClient.collectionSize(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes, null);
				//Attach the result if data is provided
				if(measure != null) method.attachPublicTag(AVG_EXEC_TIME_TAG, measure);
			    //Attach the result if data is provided
			    if(averageSize != null) method.attachPublicTag(COLLECTION_SIZE_TAG, averageSize);
			    return CONTINUE;
			}

			@Override
			public PerformanceVisitor visit(MethodOccurence method) {
				//Find the Definition location of the Method
				MethodLocator loc = method.createCorrespondingMethodLocation();
				//get the average Excecution Time for the Method
				Double measure = fddClient.avgExecTime(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes);
				//get the collection Size of the return value
			    Double averageSize = fddClient.collectionSize(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes, null);

			    //Attach the result if data is provided
				if(measure != null) method.attachTag(AVG_EXEC_TIME_TAG, measure);	
			    //Attach the result if data is provided
			    if(averageSize != null) method.attachTag(COLLECTION_SIZE_TAG, averageSize);
			    return CONTINUE;
			}

			@Override
			public PerformanceVisitor visit(ParameterDeclaration decl) {
				//Find the current Method the parameter belongs to
				MethodLocator loc = decl.getCurrentMethod().createCorrespondingMethodLocation();
				//get the collection Size of the parameter				
				Double averageSize = fddClient.collectionSize(rootContext.getProject(),loc.className, loc.methodName, loc.argumentTypes, decl.getPosition());
				//Attach the result if data is provided
				if(averageSize != null) decl.attachTag(COLLECTION_SIZE_TAG, averageSize); 
			    return CONTINUE;
			}		
		};	  
	}
}
