tmr = {
}

tmr.init = function(gameId) {
    this.gameId = gameId;
}

tmr.startGame = function() {
    $.post({
        timeout: 5000,
        url: "/tmr/startGame.mvc",
        data: { "gameId": this.gameId }
    });
    $('#startGame').remove();
};

tmr.listen = function () {
    $.post({
        cache: false,
        complete: tmr.complete,
        error: tmr.error,
        success: tmr.success,
        timeout: 5000,
        url: "/tmr/listen.mvc",
        data: {}
    });
};

tmr.complete = function () {
    console.log('complete');
    setTimeout("tmr.listen()", 5000);
};

tmr.error = function (data, status) {
    console.log('error: ' + data + ' status: ' + status);
};

tmr.success = function (data, status) {
    console.log('success: ' + data + ' status: ' + status);
    tmr.handleEvents(data.events);
};

tmr.handleEvents = function(events) {
    for (var i = 0; i < events.length; i++) {
        tmr.handleEvent(events[i], i);
    }
}

tmr.handleEvent = function(event, i) {
    tmr.eventHandlers[event.type](event, i);
}

tmr.eventHandlers = {}

tmr.eventHandlers['BlackCardDrawEvent'] = function(event, i) {
    console.log('BlackCardDrawEvent');

    var cardText = event.params['cardText'];

    var oldCardDiv = $("#blackCard");

    if (oldCardDiv) {
        oldCardDiv.delay(i * 500).animate({
            left: '-150%',
        }, 500, function() {
            oldCardDiv.remove()
        });
    }

    var blackCardDiv = $('<div id="blackCard" class="blackCard""><div class="cardText">' + cardText + '</div></div>').css("left", "150%");
    $("#blackCardContainer").append(blackCardDiv);
    blackCardDiv.delay(i * 500).animate({
        left: '50%',
    }, 500);
    
};

tmr.eventHandlers['PlayerCardCzarEvent'] = function(event, i) {
    console.log('PlayerCardCzarEvent');
};

tmr.eventHandlers['PlayerRoundEvent'] = function(event, i) {
    console.log('PlayerRoundEvent');
};

tmr.eventHandlers['WhiteCardDrawEvent'] = function(event, i) {
    console.log('WhiteCardDrawEvent');

    var cardText = event.params['cardText'];

    var whiteCardDiv = $('<div class="whiteCard"><div class="cardText">' + cardText + '</div></div>').css("margin-left", "150%");
    whiteCardDiv.click(function() {
        tmr.selectWhiteCard($(this));
    });
    $("#whiteCards").append(whiteCardDiv);
    whiteCardDiv.delay(i * 500).animate({
        marginLeft: '0%'
    }, 500);
};

tmr.eventHandlers['RoundPlayerDecisionEvent'] = function(event, i) {
    console.log('RoundPlayerDecisionEvent');

    var cardText = event.params['cardText'];
    var playedCardDiv = $('<div class="playedWhiteCard"</div>').hide();
    var playedCardTextDiv = $('<div class="cardText"' + cardText + '</div>').hide();
    playedCardDiv.append(playedCardTextDiv);
    $("#playedWhiteCards").append(playedCardDiv);
    $(this).delay(i * 500).animate({
        'height': 0,
        'width': 0
    }, 250, function() {
        playedCardDiv.fadeIn("slow");
        $(this).remove();
    });
};

tmr.eventHandlers['CardCzarDecisionEvent'] = function(event, i) {
    console.log('CardCzarDecisionEvent');
};

tmr.selectWhiteCard = function (el) {
    console.log('select white card: ' + $(this));
    if (!el.hasClass("selected")) {
        el.animate({
            backgroundColor: "#FFEC1D",
            marginTop: "10px"
        });
        el.addClass("selected");
    } else {
        el.animate({
            'opacity': 0,
        }, 750, function() {
            var playedCardDiv = $('<div class="playedWhiteCard"</div>').hide();
            $("#playedWhiteCards").append(playedCardDiv);
            el.animate({
                'height': 0,
                'width': 0
            }, 250, function() {
                playedCardDiv.fadeIn("slow");
                el.remove();
            });
        });
    }
};

$(document).ready(function () {
    $('.blackCard').click(function() {
        $('.blackCard').each(function() {
            if ($(this).offset().left < 0) {
                $(this).css("left", "150%");
            } else if ($(this).offset().left > $('#blackCardContainer').width()) {
                $(this).animate({
                    left: '50%',
                }, 500 );
            } else {
                $(this).animate({
                    left: '-150%',
                }, 500, function() {
                    $(this).remove();
                });
            }
        });
    });

    $('.whiteCard').click(function() { tmr.selectWhiteCard($(this)); });

    $('#startGame').click(function() {
        tmr.startGame();
    });

    console.log('ready');
    tmr.listen();
});