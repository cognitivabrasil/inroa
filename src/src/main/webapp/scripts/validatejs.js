/* script copiado de algum site e modificado
 *
 *  Package to validate various data :
 *  isEqual
 *  hasValidChars
 *  isSimpleIP
 *  isAlphaLatin
 *  isNotEmpty
 *  isIntegerInRange
 *  isNum
 *  isEMailAddr option:'email'
 *  isZipCode
 *  isDate
 *  isMD5
 *  isURL
 *  isGuid
 *  isISBN
 *  isSSN
 *  isDecimal
 *  isplatform
 *  addRules
 *  Apply
 *  Marcos
 *  isCPF
 *  isIP
 *  isnotnull
 *  isURLtoIP a url pode ser um ip começando por http://
 /*--------------------------------------------------------------------------*/
var Class = {
    create: function() {
        return function() {
            this.initialize.apply(this, arguments);
        }
    }
}

function getValue(s) {
    return document.getElementById(s).value
}

var Validate = Class.create();
Validate.prototype = {
    /*--------------------------------------------------------------------------*/
    initialize: function() {
        this.error_array = []
        this.rules_array = [];
        this.e = true;
    },
    /*--------------------------------------------------------------------------*/
    isEqual: function(string1, string2) {
        if (string1 == string2)
            return true;
        else
            return false;
    },
    /*--------------------------------------------------------------------------*/
    hasValidChars: function(s, characters, caseSensitive) {
        function escapeSpecials(s) {
            return s.replace(new RegExp("([\\\\-])", "g"), "\\$1");
        }
        return new RegExp("^[" + escapeSpecials(characters) + "]+$", (!caseSensitive ? "i" : "")).test(s);
    },
    /*--------------------------------------------------------------------------*/
    isSimpleIP: function(ip) {
        //ipRegExp = /^(([0-2]*[0-9]+[0-9]+)\.([0-2]*[0-9]+[0-9]+)\.([0-2]*[0-9]+[0-9]+)\.([0-2]*[0-9]+[0-9]+))$/
        ipRegExp = /^(([1]?[0-9]{1,2}|2([0-4][0-9]|5[0-5]))\.){3}([1]?[0-9]{1,2}|2([0-4][0-9]|5[0-5]))$/

        return ipRegExp.test(ip);
    },
    /*--------------------------------------------------------------------------*/
    isAlphaLatin: function(string) {
        alphaRegExp = /^[0-9a-z]+$/i
        return alphaRegExp.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isNotEmpty: function(string) {
        return /\S/.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isEmpty: function(s) {
        return !/\S/.test(s);
    },
    /*--------------------------------------------------------------------------*/
    isIntegerInRange: function(n, Nmin, Nmax) {
        var num = Number(n);
        if (isNaN(num)) {
            return false;
        }
        if (num != Math.round(num)) {
            return false;
        }
        return (num >= Nmin && num <= Nmax);
    },
    /*--------------------------------------------------------------------------*/
    isNum: function(number) {
        numRegExp = /^[0-9]+$/
        return numRegExp.test(number);
    },
    /*--------------------------------------------------------------------------*/
    isEMailAddr: function(string) {
        emailRegExp = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.([a-z]){2,4})$/
        return emailRegExp.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isZipCode: function(zipcode, country) {
        if (!zipcode)
            return false;
        if (!country)
            format = 'US';
        switch (country) {
            case'US':
                zpcRegExp = /^\d{5}$|^\d{5}-\d{4}$/;
                break;
            case'MA':
                zpcRegExp = /^\d{5}$/;
                break;
            case'CA':
                zpcRegExp = /^[A-Z]\d[A-Z] \d[A-Z]\d$/;
                break;
            case'DU':
                zpcRegExp = /^[1-9][0-9]{3}\s?[a-zA-Z]{2}$/;
                break;
            case'FR':
                zpcRegExp = /^\d{5}$/;
                break;
            case'Monaco':
                zpcRegExp = /^(MC-)\d{5}$/;
                break;
        }
        return zpcRegExp.test(zipcode);
    },
    /*--------------------------------------------------------------------------*/
    isDate: function(date, format) {
        if (!date)
            return false;
        if (!format)
            format = 'FR';

        switch (format) {
            case'FR':
                RegExpformat = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/([2][0]|[1][9])\d{2})$/;
                break;
            case'US':
                RegExpformat = /^([2][0]|[1][9])\d{2}\-([0]\d|[1][0-2])\-([0-2]\d|[3][0-1])$/;
                break;
            case'SHORTFR':
                RegExpformat = /^([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/\d{2}$/;
                break;
            case'SHORTUS':
                RegExpformat = /^\d{2}\-([0]\d|[1][0-2])\-([0-2]\d|[3][0-1])$/;
                break;
            case'dd MMM yyyy':
                RegExpformat = /^([0-2]\d|[3][0-1])\s(Jan(vier)?|F√©v(rier)?|Mars|Avr(il)?|Mai|Juin|Juil(let)?|Aout|Sep(tembre)?|Oct(obre)?|Nov(ember)?|Dec(embre)?)\s([2][0]|[1][19])\d{2}$/;
                break;
            case'MMM dd, yyyy':
                RegExpformat = /^(J(anuary|u(ne|ly))|February|Ma(rch|y)|A(pril|ugust)|(((Sept|Nov|Dec)em)|Octo)ber)\s([0-2]\d|[3][0-1])\,\s([2][0]|[1][9])\d{2}$/;
                break;
        }

        return RegExpformat.test(date);
    },
    /*--------------------------------------------------------------------------*/
    isMD5: function(string) {
        if (!string)
            return false;
        md5RegExp = /^[a-f0-9]{32}$/;
        return md5RegExp.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isURL: function(string) {
        if (!string)
            return false;
        string = string.toLowerCase();
        urlRegExp = /^(((ht|f)tp(s?))\:\/\/)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$/

        return urlRegExp.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isURLtoIP: function(string) {
        if (!string)
            return false;
        string = string.toLowerCase();
        urlRegExp = /^(((ht|f)tp(s?))\:\/\/)([0-9a-zA-Z\-\.]+)(\:[0-9]+)?(\/\S*)?$/
        return urlRegExp.test(string);
    },
    /*--------------------------------------------------------------------------*/
    isGuid: function(guid) {//guid format : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx or xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        if (!guid)
            return false;
        GuidRegExp = /^[{|\(]?[0-9a-fA-F]{8}[-]?([0-9a-fA-F]{4}[-]?){3}[0-9a-fA-F]{12}[\)|}]?$/
        return GuidRegExp.test(guid);
    },
    /*--------------------------------------------------------------------------*/
    isISBN: function(number) {
        if (!number)
            return false;
        ISBNRegExp = /ISBN\x20(?=.{13}$)\d{1,5}([- ])\d{1,7}\1\d{1,6}\1(\d|X)$/
        return ISBNRegExp.test(number);
    },
    /*--------------------------------------------------------------------------*/
    isSSN: function(number) {//Social Security Number format : NNN-NN-NNNN
        if (!number)
            return false;
        ssnRegExp = /^\d{3}-\d{2}-\d{4}$/
        return ssnRegExp.test(number);
    },
    /*--------------------------------------------------------------------------*/
    isDecimal: function(number) {// positive or negative decimal
        if (!number)
            return false;
        decimalRegExp = /^-?(0|[1-9]{1}\d{0,})(\.(\d{1}\d{0,}))?$/
        return decimalRegExp.test(number);
    },
    /*--------------------------------------------------------------------------*/
    isplatform: function(platform) {
        //win, mac, nix
        if (!platform)
            return false;
        var os;
        winRegExp = /\win/i
        if (winRegExp.test(window.navigator.platform))
            os = 'win';

        macRegExp = /\mac/i
        if (macRegExp.test(window.navigator.platform))
            os = 'mac';

        nixRegExp = /\unix|\linux|\sun/i
        if (nixRegExp.test(window.navigator.platform))
            os = 'nix';

        if (platform == os)
            return true;
        else
            return false;
    },
    /*--------------------------------------------------------------------------*/
    isCPF: function(string) {
        var cpf = string;
        exp = /\.|\-/g
        cpf = cpf.toString().replace(exp, "");
        var digitoDigitado = eval(cpf.charAt(9) + cpf.charAt(10));
        var soma1 = 0, soma2 = 0;
        var vlr = 11;

        for (i = 0; i < 9; i++) {
            soma1 += eval(cpf.charAt(i) * (vlr - 1));
            soma2 += eval(cpf.charAt(i) * vlr);
            vlr--;
        }
        soma1 = (((soma1 * 10) % 11) == 10 ? 0 : ((soma1 * 10) % 11));
        soma2 = (((soma2 + (2 * soma1)) * 10) % 11);

        var digitoGerado = (soma1 * 10) + soma2;
        if (digitoGerado != digitoDigitado)
            return false;

        return true;
    },
    /*--------------------------------------------------------------------------*/
    isnotnull: function(string) {
        if (string != 'null')
            return true;
        else
            return false;
    },
    /*--------------------------------------------------------------------------*/
    getValue: function(id) {
        document.getElementById(id).value;
    },
    /*--------------------------------------------------------------------------*/
    addRules: function(rules) {
        this.rules_array.push(rules);
    },
    /*--------------------------------------------------------------------------*/
    check: function() {
        this.error_array = [];
        this.e = true;
        for (var i = 0; i < this.rules_array.length; i++) {
            switch (this.rules_array[i].option) {
                /*--------------------------------------------------------------------------*/
                case'ValidChars':
                    if (!this.hasValidChars(getValue(this.rules_array[i].id), this.rules_array[i].chars, false)) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'AlphaLatin':
                    if (this.isAlphaLatin(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'required':
                    if (this.isEmpty(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'integerRange':
                    if (!this.isIntegerInRange(getValue(this.rules_array[i].id), this.rules_array[i].Min, this.rules_array[i].Max)) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'Number':
                    if (!this.isNum(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'email':
                    if (!this.isEMailAddr(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'zipCode':
                    if (!this.isZipCode(getValue(this.rules_array[i].id), this.rules_array[i].country)) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'date':
                    if (!this.isDate(getValue(this.rules_array[i].id), this.rules_array[i].format)) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'url':
                    if (!this.isURL(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;

                    /*--------------------------------------------------------------------------*/
                case'urlcomip':
                    if (!this.isURLtoIP(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'Decimal':
                    if (!this.isDecimal(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'isEqual':
                    if (!this.isEqual(getValue(this.rules_array[i].id), getValue(this.rules_array[i].to))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'isCPF':
                    if (!this.isCPF(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'isIP':
                    if (!this.isSimpleIP(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/
                case'isnotnull':
                    if (!this.isnotnull(getValue(this.rules_array[i].id))) {
                        this.error_array.push(this.rules_array[i].error);
                        this.e = false;
                    }
                    break;
                    /*--------------------------------------------------------------------------*/

            }
        }
    },
    /*--------------------------------------------------------------------------*/
    Apply: function(el) {
        this.check();
        if (this.e) {
            return true;
        } else {
            var endMsg = this.error_array;
            if (!el) {
                alert(this.error_array.toString().replace(/\,/gi, "\n"));
            } else {
                document.getElementById(el).innerHTML = this.error_array.toString().replace(/\,/gi, "<br/>");
            }
            return false;
        }
    }
    /*--------------------------------------------------------------------------*/
}
