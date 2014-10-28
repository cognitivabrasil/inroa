var autoSuggest = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  remote: './suggestion?query=%QUERY'
	});
	 
	autoSuggest.initialize();
	 
	$('#consulta').typeahead(
		{
			  minLength: 2,
			  highlight: true,
			  hint: false
		}, {
	  name: 'auto-suggest',
	  displayKey: 'value',
	  hightlight: true,
	  source: autoSuggest.ttAdapter()
	});
	
	$('#consulta').on("typeahead:autocompleted", function(e, suggestion) {
		console.log(suggestion);
	});
	
	$('#consulta').on("typeahead:selected", function(e, suggestion) {
		console.log(suggestion);
		$("#busca").click();
});