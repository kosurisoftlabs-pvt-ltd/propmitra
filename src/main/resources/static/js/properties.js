function applyMetaData(meta) {
    if(meta != null) {
        //set rentSale
        if(meta.rentSale != null) {
            $('.filters').tagsinput('add', { "value": "rentOrSale", "text": meta.rentSale, "type": "radio" });
        }
        if(meta.keywords != null) {
            var keywords = meta.keywords;
            //console.log('availableTags', keywords);
            $( "#keywords" ).autocomplete({
              source: keywords,
              select: function (event, ui) {
                if(ui.item.value != "") {
                    $('.filters').tagsinput('add', { "value": 'keyword_'+ui.item.label, "text": ui.item.label, "type": "checkbox" });
                    ui.item.value = "";
                }
              }
            });
        }

        if(meta.minPrice != null && meta.maxPrice != null) {
            var ranges = {}
            var stepVal = 1000;
            if(meta.maxPrice > 1000000) {
                stepVal = 100000;
            }
            $( "#slider" ).slider({
                range: true,
                min: 0,
                max: meta.maxPrice,
                step: stepVal,
                values: [ meta.minPrice, meta.maxPrice ],
                slide: function( event, ui ) {
                    $( "#amount" ).val( convert2INR(ui.values[ 0 ]) + " - " + convert2INR(ui.values[ 1 ]) );
                    $('.filters').tagsinput('remove', { "value": 'minPrice' });
                    $('.filters').tagsinput('remove', { "value": 'maxPrice' });
                    $('.filters').tagsinput('add', { "value": 'minPrice', "text": ui.values[ 0 ] });
                    $('.filters').tagsinput('add', { "value": 'maxPrice', "text": ui.values[ 1 ] });
                  }
            })
            .each(function() {
              var opt = $(this).data().uiSlider.options;

              appendSliderLabels(opt.max);

             /* // Get the number of possible values
              var vals = opt.max - opt.min;

              // Space out values
              for (var i = opt.min; i <= vals;) {
                 var el = '';
                 if(i >= 10000000) {
                    el = $('<label>'+((i/10000000.00).toFixed(1))+' Cr</label>').css('left',(i/vals*100)+'%');
                    i+=10000000
                 } else if(vals >= 10000000) {
                    el = $('<label>'+((i/10000000.00).toFixed(1))+' Cr</label>').css('left',(i/vals*100)+'%');
                    i+=5000000
                 } else  {
                    el = $('<label>'+((i/100000.00).toFixed(0))+'L</label>').css('left',(i/vals*100)+'%');
                    i+=500000
                 }

                $( "#slider" ).append(el);

              }*/


            });
            $( "#amount" ).val( convert2INR($( "#slider" ).slider( "values", 0 )) +
                          " - " + convert2INR($( "#slider" ).slider( "values", 1 )) );
            //$('.filters').tagsinput('add', { "value": 'minPrice', "text": $( "#slider" ).slider( "values", 0 ) });
            //$('.filters').tagsinput('add', { "value": 'maxPrice', "text": $( "#slider" ).slider( "values", 1 ) });
        }
    }

    var bhk = $.urlParam('bhk');
    if (bhk != null) {
        //console.log('bhk', bhk);
        var bhkarr = bhk.split(",");
        $.each(bhkarr, function (i) {
            switch (bhkarr[i]) {
              case "1%20BHK":
                $( "#bhk1").prop('checked', true);
                $('.filters').tagsinput('add', { "value": 'bhk1', "text": '1 BHK', "type": "checkbox" });
                break;
              case "2%20BHK":
                  $( "#bhk2").prop('checked', true);
                  $('.filters').tagsinput('add', { "value": 'bhk2', "text": '2 BHK', "type": "checkbox" });
                  break;
              case "3%20BHK":
                $( "#bhk3").prop('checked', true);
                $('.filters').tagsinput('add', { "value": 'bhk3', "text": '3 BHK', "type": "checkbox" });
                break;
              case "4%20BHK":
                  $( "#bhk4").prop('checked', true);
                  $('.filters').tagsinput('add', { "value": 'bhk4', "text": '4 BHK', "type": "checkbox" });
                  break;
              case "5%20BHK":
                $( "#bhk5").prop('checked', true);
                $('.filters').tagsinput('add', { "value": 'bhk5', "text": '5 BHK', "type": "checkbox" });
                break;
              case "6%20BHK":
                  $( "#bhk6").prop('checked', true);
                  $('.filters').tagsinput('add', { "value": 'bhk6', "text": '6 BHK', "type": "checkbox" });
                  break;
            }
        });
    }

    var listBy = $.urlParam('listBy');
    if (listBy != null) {
        //console.log('bhk', bhk);
        var listByArr = listBy.split(",");
        $.each(listByArr, function (i) {
            switch (listByArr[i]) {
              case "Agent":
                $( "#postBy1").prop('checked', true);
                $('.filters').tagsinput('add', { "value": 'postBy1', "text": 'Agent', "type": "checkbox" });
                break;
              case "Property%20Owner":
                  $( "#postBy2").prop('checked', true);
                  $('.filters').tagsinput('add', { "value": 'postBy2', "text": 'Property Owner', "type": "checkbox" });
                  break;
              case "Developer":
                $( "#postBy3").prop('checked', true);
                $('.filters').tagsinput('add', { "value": 'postBy3', "text": 'Developer', "type": "checkbox" });
                break;
            }
        });
    }
}

$('.filters').on('beforeItemRemove', function(event) {
    var tag = event.item;
    if(tag.type == 'select') {
       //$('#'+tag.value).val('select');
        //console.log('select', tag);
    } else if(tag.type == 'checkbox') {
        $('#'+tag.value).prop('checked', false);
    }
});

$('input[name=rentOrSale]').change(function () {
    $("body").addClass("loading");
    $.ajax({
        method: "GET",
        url: "/api/search/meta/"+this.value,
        processData: false,
        contentType:"application/json; charset=utf-8",
        success: function(response){
            $("body").removeClass("loading");
            if(response) {
                $("#property-content").html(response);
            }
        }
    });
    return false;
});

$('input[type=checkbox]').change(function () {
    if ($(this).prop("checked")) {
        $('.filters').tagsinput('add', { "value": this.id, "text": this.value, "type": "checkbox" });
        //console.log('add', this);
     } else {
        //console.log('remove', this);
        $('.filters').tagsinput('remove', { "value": this.id });
     }

});

$('input[type=radio]').change(function () {
    if ($(this).prop("checked")) {
        $('.filters').tagsinput('remove', { "value": this.name });
        $('.filters').tagsinput('add', { "value": this.name, "text": this.value, "type": "radio" });
        //console.log('add', this);
     } else {
        //console.log('remove', this);
     }

});

$(document).ready(function () {
    $('.bootstrap-tagsinput input').attr('readonly', 'readonly');
    $("#filterForm").submit(function (e) {
        e.preventDefault();
        var filters = $(".filters").tagsinput('items');
        var location = "";
        var rentOrSale = "";
        var keywords = [];
        var minPrice = "";
        var maxPrice = "";
        var bhk = [];
        var propType = [];
        var listBy = [];
        var propStatus = [];

        if(filters != undefined && filters.length > 0) {
            for(var i in filters) {
                if(filters[i].value.indexOf('location') != -1){
                    location = filters[i].text;
                } else if(filters[i].value.indexOf('rentOrSale') != -1){
                   rentOrSale = filters[i].text;
               } else if(filters[i].value.indexOf('bhk') != -1){
                    bhk.push(filters[i].text);
                } else if(filters[i].value.indexOf('type') != -1){
                    propType.push(filters[i].text);
                } else if(filters[i].value.indexOf('status') != -1){
                    propStatus.push(filters[i].text);
                } else if(filters[i].value.indexOf('postBy') != -1){
                    listBy.push(filters[i].text);
                } else if(filters[i].value.indexOf('keyword') != -1){
                    keywords.push(filters[i].text);
                } else if(filters[i].value.indexOf('minPrice') != -1){
                    minPrice = filters[i].text;
                } else if(filters[i].value.indexOf('maxPrice') != -1){
                    maxPrice = filters[i].text;
                }
            }
        }

        //console.log("location", location);
        //console.log("rentOrSale", rentOrSale);
        //console.log("keywords", keywords);
        //console.log("minprice", minPrice);
        //console.log("maxprice", maxPrice);
        //console.log("bhk", bhk);
        //console.log("propType", propType);
        //console.log("listBy", listBy);
        //console.log("propStatus", propStatus);

        /*console.log(JSON.stringify({ 'location' : location, 'rentOrSale' : rentOrSale, 'keywords' : keywords,
                                'minPrice' : minPrice, 'maxPrice' : maxPrice, 'bhk' : bhk,
                                'propType' : propType, 'listBy': listBy, 'propStatus':propStatus }));*/
        $("body").removeClass("loading").addClass("loading");
        $.ajax({
            method: "POST",
            url: "/properties",
            data: JSON.stringify({ 'location' : location, 'rentOrSale' : rentOrSale, 'keywords' : keywords, 'minPrice' : minPrice, 'maxPrice' : maxPrice, 'bhk' : bhk,
            'propType' : propType, 'listBy': listBy, 'propStatus':propStatus }),
            processData: false,
            contentType:"application/json; charset=utf-8",
            //dataType:"json",
            success: function(response){
                $("body").removeClass("loading");
                if(response) {
                    //console.log('response', response);
                    $("#content").html(response);
                }
            }
        });
        return false;
    });
});


function convert2INR(val) {
    if(val >= 10000000) val = (val/10000000).toFixed(2) + ' Cr';
    else if(val >= 100000) val = (val/100000).toFixed(2) + ' Lac';
    else if(val >= 1000) val = (val/1000).toFixed(2) + ' K';
    return val;
}

function appendSliderLabels(max) {
    if(max <= 75000) {
        $( "#slider" ).slider( "option", "max", 75000 );
        $( "#slider" ).append($('<label>0</label>').css('left','0%'));
        $( "#slider" ).append($('<label>15K</label>').css('left', (1/5*100)+'%'));
        $( "#slider" ).append($('<label>30K</label>').css('left', (2/5*100)+'%'));
        $( "#slider" ).append($('<label>45K</label>').css('left', (3/5*100)+'%'));
        $( "#slider" ).append($('<label>60K</label>').css('left', (4/5*100)+'%'));
        $( "#slider" ).append($('<label>75K</label>').css('left', (5/5*100)+'%'));
    } else if(max <= 5000000) {
        $( "#slider" ).slider( "option", "max", 5000000 );
        $( "#slider" ).append($('<label>0</label>').css('left','0%'));
        $( "#slider" ).append($('<label>10L</label>').css('left', (1/5*100)+'%'));
        $( "#slider" ).append($('<label>20L</label>').css('left', (2/5*100)+'%'));
        $( "#slider" ).append($('<label>30L</label>').css('left', (3/5*100)+'%'));
        $( "#slider" ).append($('<label>40L</label>').css('left', (4/5*100)+'%'));
        $( "#slider" ).append($('<label>50L</label>').css('left', (5/5*100)+'%'));
    } else if(max <= 7500000) {
       $( "#slider" ).slider( "option", "max", 7500000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>15L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>30L</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>45L</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>60L</label>').css('left', (4/5*100)+'%'));
       $( "#slider" ).append($('<label>75L</label>').css('left', (5/5*100)+'%'));
   } else if(max <= 12500000) {
       $( "#slider" ).slider( "option", "max", 12500000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>25L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>50L</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>75L</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>1Cr</label>').css('left', (4/5*100)+'%'));
       var a = -30+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>1.25Cr</label>').css({left: b, marginLeft: a}));
   } else if(max <= 15000000) {
       $( "#slider" ).slider( "option", "max", 15000000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>30L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>60L</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>90L</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>1.2Cr</label>').css('left', (4/5*100)+'%'));
       var a = -30+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>1.5Cr</label>').css({left: b, marginLeft: a}));
   } else if(max <= 15000000) {
       $( "#slider" ).slider( "option", "max", 15000000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>30L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>60L</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>90L</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>1.2Cr</label>').css('left', (4/5*100)+'%'));
       var a = -30+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>1.5Cr</label>').css({left: b, marginLeft: a}));
   } else if(max <= 20000000) {
       $( "#slider" ).slider( "option", "max", 20000000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>40L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>80L</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>1.2Cr</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>1.6Cr</label>').css('left', (4/5*100)+'%'));
       var a = -15+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>2Cr</label>').css({left: b, marginLeft: a}));
   } else if(max <= 25000000) {
       $( "#slider" ).slider( "option", "max", 25000000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>50L</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>1Cr</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>1.5Cr</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>2Cr</label>').css('left', (4/5*100)+'%'));
       var a = -30+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>2.5Cr</label>').css({left: b, marginLeft: a}));
   } else if(max <= 50000000) {
       $( "#slider" ).slider( "option", "max", 50000000 );
       $( "#slider" ).append($('<label>0</label>').css('left','0%'));
       $( "#slider" ).append($('<label>1Cr</label>').css('left', (1/5*100)+'%'));
       $( "#slider" ).append($('<label>2Cr</label>').css('left', (2/5*100)+'%'));
       $( "#slider" ).append($('<label>3Cr</label>').css('left', (3/5*100)+'%'));
       $( "#slider" ).append($('<label>4Cr</label>').css('left', (4/5*100)+'%'));
       var a = -15+'px';
       var b = (5/5*100)+'%';
       $( "#slider" ).append($('<label>5Cr</label>').css({left: b, marginLeft: a}));
   }

   $.urlParam = function (name) {
       var results = new RegExp('[\?&]' + name + '=([^&#]*)')
                         .exec(window.location.search);
       return (results !== null) ? results[1] || 0 : null;
   }

}
