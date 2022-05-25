var schedulesearch = ( function() {
    
   
    return {

        validateSelection: function() {
                var terms = document.getElementById("subjectid");
                if (terms.value === "") {
                    alert("You must select a SUBJECT. Try again"); 
                    return false;
                }
                return true;
        }, 
          
        addOrRemoveDay: function(){
                days = $('input[type="checkbox"]:checked').map(function() {
                    return this.value;
                }).get().join('');
                console.log(days);
                $('#days').val(days);
        },
        
        get24HourTime: function(h, m, ampm){
                console.log(ampm === "pm" &&  h !== "12");
                if (ampm === "p" &&  h !== "12"){
                    var starthour_int = parseInt(h) + 12;
                    h = starthour_int.toString();
                }
                else if (ampm === "a" && h === "12")
                    h = "00";
               
                return  h + ":" + m;
        }

    };

}());

