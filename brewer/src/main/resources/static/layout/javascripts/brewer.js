var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function(){
		this.decimal.maskMoney({decimal: ',', thousands: '.'});
		this.plain.maskMoney({precision: 0, thousands: '.'});
	}
	
	return MaskMoney;
	
}());

//Função chama a máscara do telefone para a classe js-phone-number
Brewer.MaskPhoneNumber = (function() {
	
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var options = {
			onKeyPress: function(val, e, field, options) {
				field.mask(maskBehavior.apply({}, arguments), options);	
			}
		};
		
		this.inputPhoneNumber.mask(maskBehavior, options);
		
	}
	
	return MaskPhoneNumber;
	
}());

//Função para a máscara do CEP
Brewer.MaskCep = (function() {
	
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');
	}
	
	return MaskCep;
	
	
}());


$(function(){
	//Chama a função de máscara para moeda
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	//Chama a função de Máscara do telefone
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	//Chama a função de máscara para CEP
	var maskCep = new Brewer.MaskCep();
	maskCep.enable();
	
	
	
});