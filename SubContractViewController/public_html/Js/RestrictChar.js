function restrictInput(adfKeyPressEvent, numericExpression) {
    var nativeKeyPressEvent = adfKeyPressEvent.getNativeEvent();
    var key = nativeKeyPressEvent.keyCode;
    var char = nativeKeyPressEvent.charCode;
    var control = nativeKeyPressEvent.ctrlKey;
    var alt = nativeKeyPressEvent.altKey;
    var userAgent = navigator.userAgent;
    var enteredChar = String.fromCharCode(key | char);
    var valid = numericExpression.test(enteredChar);
	if(numericExpression.source.indexOf(' ')>=0 && key==32){
		valid=true;
	}
          var enterKey = 13;
          var delKey = 46
          var bkpspKey = 8;
          var tabKey = 9;
          var instKey = 45;
          var shiftKey = 16;
          var escKey = 27;
          var rArrKey = 39;
          var lArrKey = 37;
    if(userAgent.indexOf("Firefox") > -1){
        if (key !== enterKey && key !== delKey && key !== bkpspKey && key !== rArrKey && key !== lArrKey &&
            key !== tabKey && key !== instKey && key !== shiftKey && key !== escKey && key !== escKey && !control && !alt) {
            if(!valid){
                adfKeyPressEvent.cancel();
            }
        }
    }else{
        if(!valid){
            adfKeyPressEvent.cancel();
        }
    }
};
function allowDecimalNumericInput(adfKeyPressEvent) {
	var allowedChars = /^[0-9.]+$/;
	restrictInput(adfKeyPressEvent, allowedChars);
};
function allowOnlyNumericInput(adfKeyPressEvent) {
	var allowedChars = /^[0-9]+$/;
	restrictInput(adfKeyPressEvent, allowedChars);
};

function onKeyPressing(event){
if(event.which < 48 || event.which > 57 ) if(event.which != 8) return false;
}
