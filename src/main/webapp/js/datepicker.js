$("#returndiv").hide();
$("input[name=radiogroup]").click(function() 
    {
        if ( $("#out").prop('checked'))
            $("#returndiv").hide();
        else
            $("#returndiv").show();
    });