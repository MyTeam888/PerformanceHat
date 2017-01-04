<style>

* { 
  font-family: Arial;
  font-size: 14px; 
}

.highlight { 
  font-size: 16px;
  font-weight: bold;
}

</style>

<#macro timeNode node indent>
		<#local spc>${""?right_pad(indent*6*4, "&nbsp;")}</#local>
		<#if node.isDataNode()>
		   	<tr>
		     	<td style='padding-right: 20px;'>${spc}${node.getText()}:</td>
				<#if !singleLineMode>	
	    	 		<#list node.getPredictedText() as text>
						<td>${text}</td>
		  			</#list>
		    	<#else>
		    		<td>${node.getPredictedText()[0]}</td>		    	
		    	</#if>
	    	</tr>
	    	<#list node.getChildren() as child>
				<@timeNode node=child indent=indent+1 />
	  		</#list>
	    <#else>
		    <#list node.getChildren() as child>
				<@timeNode node=child indent=indent />
	  		</#list>
	    </#if>
</#macro>

<p>This loop has been identified as critical.</p>
<p><span class="highlight">The Average Total Time is: ${avgTotal}</span></br>
Average Iterations: ${avgInterations}; Average Time per Iteration: ${avgTimePerIteration}</p>

<div><strong>Measured Operations:</strong></div>
<table border="0" cellspacing="0">
		<#if !singleLineMode>
			<tr>
				<td><b>Measurements</b></td>
				<#list procedureExecutions.getHeader().getText() as headerText>
					<td><b>${headerText}</b></td>
		  		</#list>
			</tr>
	    </#if>	
		<@timeNode node=procedureExecutions indent=0 />
</table>