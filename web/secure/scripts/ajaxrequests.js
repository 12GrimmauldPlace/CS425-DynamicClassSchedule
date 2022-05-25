var request = ( function() {
    
    var courses = 0;
   
    return {

        getCourses: function(termid) {
            
            var that = this;
                $.ajax({
                    url: 'RegisterServlet', 
                    method: 'GET', 
                    data: {"termid": termid}, 
                    dataType: 'json', 
                    success: function(response) {
                        if(response.length === 0){
                            $("#registeredcourses").html("<p>You are not currently registered for any courses for this term.</p>"); 
                        }
                        else{
                            that.createTable(response); 
                        }
                        console.log(response);
                    }            
                });
        },

       registerCourse: function(termid) {
           if($("#crn").val() === ""){
               alert("You must enter a value");
               return false;
           }
            var that = this;
            $.ajax({
                url: 'RegisterServlet',
                    method: 'PUT',
                    data: 
                        {
                            'crn': $('#crn').val(), 
                            'termid': termid 
                        },
                    
                    dataType: 'json',
                    success: function(response) {
                        console.log(response);
                        that.createTable(response);
                    }
            });      
        }, 
        
        dropCourse: function(termid) {
            if($("#crn").val() === ""){
               alert("You must enter a value");
               return false;
           }
            var that = this;
            $.ajax({
                url: 'DropServlet',
                    method: 'DELETE',
                    data: 
                        {
                            'crn': $('#crn').val(), 
                            'termid': termid 
                        },
                    
                    dataType: 'json',
                    success: function(response) {
                        if(response.length === 0){
                            $("#registeredcourses").html("<p>You are no longer registered for any courses for this term.</p>"); 
                        }
                        else{
                            that.createTable(response); 
                        }
                        console.log(response);
                    }
            });      
        }, 
        
        
        createTable: function(response){
            var table = document.createElement("table");
            table.setAttribute("border", "1");
            var tableHeader, tableRow, tableData;
            //Create headers
            tableRow = document.createElement("tr");
            for (var _key in response[0]){
                if(response[0].hasOwnProperty(_key)){
                    tableHeader = document.createElement("th");
                    $(tableHeader).html(_key).css("text-align", "left");
                    $(tableRow).append(tableHeader);
                    $(table).append(tableRow);
                }
            }
            //Create table data
            for (var i = 0; i < response.length; ++i){
                tableRow = document.createElement("tr");
                for (var key in response[i]){
                    if(response[i].hasOwnProperty(key)){
                        tableData = document.createElement("td");
                        $(tableData).html(response[i][key]);
                        $(tableRow).append(tableData);    
                        $(table).append(tableRow);  
                    }
                }
            }
            $("#registeredcourses").html(table); 
        }
        

    };

}());

