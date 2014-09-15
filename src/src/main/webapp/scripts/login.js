var myForm = new Validate();

myForm.addRules({
    id: 'user',
    option: 'required',
    error: '* Voc&ecirc; deve informar o seu nome de usu&aacute;rio!'
});
myForm.addRules({
    id: 'passwd',
    option: 'required',
    error: '* Voc&ecirc; deve informar sua senha!'
});