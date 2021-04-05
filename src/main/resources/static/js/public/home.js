function applyMeta(meta) {

    if(meta != null) {
        $("body").addClass("loading");
        if(meta.rentSale != null && meta.rentSale == 'Rent') {
            showForm('rent');
        } else {
            showForm('buy');
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
                values: [ 0, meta.maxPrice ],
                slide: function( event, ui ) {
                    $( "#amount" ).val( convert2INR(ui.values[ 0 ]) + " - " + convert2INR(ui.values[ 1 ]) );
                    $('#minPrice').val(ui.values[0]);
                    $('#maxPrice').val(ui.values[1]);
                    /*$('.filters').tagsinput('remove', { "value": 'minPrice' });
                    $('.filters').tagsinput('remove', { "value": 'maxPrice' });
                    $('.filters').tagsinput('add', { "value": 'minPrice', "text": ui.values[ 0 ] });
                    $('.filters').tagsinput('add', { "value": 'maxPrice', "text": ui.values[ 1 ] });*/
                  }
            })
            .each(function() {
              var opt = $(this).data().uiSlider.options;
              //appendSliderLabels(opt.max);
            });
            $( "#amount" ).val( convert2INR($( "#slider" ).slider( "values", 0 )) + " - " + convert2INR($( "#slider" ).slider( "values", 1 )) );
        }
        $("body").removeClass("loading");
    } else {
        showForm('buy');
        $( "#slider" ).slider({
            range: true,
            min: 0,
            max: 0,
            step: 0,
            values: [ 0, 0 ],
            slide: function( event, ui ) {
                $("#amount" ).val( convert2INR(ui.values[ 0 ]) + " - " + convert2INR(ui.values[ 1 ]) );
                $('#minPrice').val(ui.values[0]);
                $('#maxPrice').val(ui.values[1]);
              }
        });
    }
}

function showForm(listType) {
    if(listType == 'buy') {
        $('.leg-buy').show();
        //$('.sec-buy').show();
        //$('.sec-rent').hide();
        $('.leg-rent').hide();
        $('.li-buy').removeClass('active').addClass('active');
        $('.li-rent').removeClass('active');
        $('#rentOrSale').val('Sale');
    } else if(listType == 'rent') {
       $('.leg-rent').show();
       //$('.sec-rent').show();
       $('.leg-buy').hide();
       //$('.sec-buy').hide();
       $('.li-rent').removeClass('active').addClass('active');
       $('.li-buy').removeClass('active');
       $('#rentOrSale').val('Rent');
   }
}


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

}
