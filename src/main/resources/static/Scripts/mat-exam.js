(function() {
    var questions = [{
        question: "Bir kırtasiyede her birinde 12 kalem olan 81 kutu kurşun kalem vardır.Tükenmez kalem sayısı ise kurşun kalemlerden 39 daha azdır.Buna göre kırtasiyedeki tükenmez kalem sayısı kaçtır?",
        choices: [927, 914, 933, 940, 908],
        correctAnswer: 2
    }, {
        question: "175 yolcusu olan bir gemi dört limana uğramış ve her limanda 118 yolcu almıştır.\n" +
            "Buna göre gemide kaç yolcu olmuştur?",
        choices: [600, 647, 595, 741, 804],
        correctAnswer: 1
    }, {
        question: "Süleyman üç günde toplam 995 m koşmuştur.İlk iki günde her gün 385 m koştuğuna göre Süleyman 3. günde kaç metre koşmuştur?",
        choices: [350, 300, 250, 225, 220],
        correctAnswer: 3
    }, {
        question: "Büyük bir pastane yaptığı keklerin her biri için 12 yumurta kullanmaktadır.Bugün bu keklerden 29 kek yapan pastanede 154 yumurta kalmıştır.Buna göre kek yapmaya başlamdan önce pastanede kaç yumurta vardı?",
        choices: [502, 500, 501, 499, 502],
        correctAnswer: 0
    }, {
        question: "Bir lokum dükkanında 955 adet ambalajlanmamış lokum vardı.Bu lokumlardan bir kısmı bir tanesinde 35 tane lokum olan 24 kutuya konulmuştur.Ambalajlanmamış kaç lokum kalmıştır?",
        choices: [110, 115, 120, 105, 125],
        correctAnswer: 1
    }, {
        question: "Bir arabanın bagajında 8 koli vardır.Bunlardan 7 tanesinin her birinin kütlesi 113 kg , 8. kolinin kütlesi ise 185 kg dır.Buna göre bagajdaki kolilerin kütlesi kaç kilogramdır?",
        choices: [965, 970, 976, 977, 980],
        correctAnswer: 2
    }, {
        question: "342 + 133 =? işleminin tahmini sonucu kaçtır?",
        choices: [500, 480, 450, 460, 470],
        correctAnswer: 4
    }, {
        question: "Kare ile dikdörtgensel bölge hangi geometrik şeklin yüzeylerini oluşturur ?",
        choices: ["Küp", "Kare Prizma", "Daire", "Üçgen", "Dikdörtgen"],
        correctAnswer: 1
    }, {
        question: "234 + 358 =? işleminin tahmini sonucu kaçtır?",
        choices: [490, 590, 600, 450, 400],
        correctAnswer: 1
    }, {
        question: "Dört tarafı birbirine eşit olan geometrik şekil hangisidir ?",
        choices: ["Silindir", "Kare", "Üçgen", "Daire", "Dikdörtgen"],
        correctAnswer: 1
    }];

    var questionCounter = 0; //Soru sayısını takip etmek için
    var selections = []; // Kullanıcın seçtiği seçenekler
    var quiz = $('#quiz'); //Quiz div objesi

    var scoreDB;  // Skor değerini DB'ye atmak için
    var url = (window.location).href;
    var id = url.substring(url.lastIndexOf('/') + 1);



    // sayfa ilk yüklendiğinde ekranda gözükecekler
    window.onload = function () {
        $('#next').hide();
        $('#finish-exam').hide();
        $('#go-home').hide();
        $('#timer').hide();
    };
    // Sonraki soruları göstermek için
    $('#next').on('click', function (e) {
        e.preventDefault();
        choose();
        questionCounter++;
        displayNext();
    });

    // Önceki sorular için
    $('#prev').on('click', function (e) {
        e.preventDefault();
        choose();
        questionCounter--;
        displayNext();
    });



    // quiz div oluşturma
    function createQuestionElement(index) {
        var qElement = $('<div>', {
            id: 'question'
        });

        var header = $('<h2>Soru ' + (index + 1) + ':</h2>');
        qElement.append(header);

        var question = $('<p>').append(questions[index].question);
        qElement.append(question);

        var radioButtons = createRadios(index);
        qElement.append(radioButtons);

        return qElement;
    }

    // Seçenekler için radio butonları
    function createRadios(index) {
        var radioList = $('<ul>');
        var item;
        var input = '';
        for (var i = 0; i < questions[index].choices.length; i++) {
            item = $('<li>');
            input = '<input type="radio" name="answer" value=' + i + ' />';
            input += questions[index].choices[i];
            item.append(input);
            radioList.append(item);
        }
        return radioList;
    }

    // Kullanıcının seçtiklerini alır diziye atar
    function choose() {
        selections[questionCounter] = +$('input[name="answer"]:checked').val();
    }

    // Sonraki soruyu göstermek için
    function displayNext() {
        quiz.fadeOut(function() {
            $('#question').remove();

            if(questionCounter < questions.length){
                var nextQuestion = createQuestionElement(questionCounter);
                quiz.append(nextQuestion).fadeIn();
                if (!(isNaN(selections[questionCounter]))) {
                    $('input[value='+selections[questionCounter]+']').prop('checked', true);
                }

                //butonları duruma göre ekranda gösterir
                if(questionCounter === 1){
                    $('#prev').show();
                    $('#start-exam').hide();
                } else if(questionCounter === 0){
                    $('#prev').hide();
                    $('#next').show();
                }
            }else {
                var scoreElem = computeScore();
                quiz.append(scoreElem).fadeIn();
                $('#next').hide();
                $('#prev').hide();
                $('#start').show();
                $('#finish-exam').hide();
                $('#go-home').show();
                timer2 = document.querySelector('#timer').textContent;
                timetoDB = parseInt(timer2);
                clearTimeout(time);

                $(document).ready(function() {
                    $("#go-home").click(function(){

                        matscore = scoreDB;

                        $.ajax({
                            url: "?matscore="+matscore+"&time="+timetoDB,
                            type: "GET",
                            success: function(response)
                            {
                                console.log("sucess!");
                            },
                            error: function(e){
                                console.log("ERROR: ", e);
                            }
                        });


                        window.location="http://localhost:8080/exam/mat/"+id+"/?matscore="+matscore+"&time="+timetoDB;


                    });
                });
            }
        });
    }



    // Skor hesaplar ve question elementinde gösterir
    function computeScore() {
        var score = $('<p>',{id: 'question'});
        var scoreWithTime = 0;
        var numCorrect = 0;

        // Kullanıcının doğru sayısını hesaplar
        for (var i = 0; i < selections.length; i++) {
            if (selections[i] === questions[i].correctAnswer) {
                numCorrect++;
            }
        }

        //  Süreyi alır int'e dönüştürür
        timer2 = document.querySelector('#timer').textContent;
        timer2 = parseInt(timer2);


        // Süreye göre kullanıcı puanını hesaplar
        if(timer2<=10){
            scoreWithTime = numCorrect * 5;
        }else if(timer2>10 && timer2<=30){
            scoreWithTime = numCorrect * 7
        }else{
            scoreWithTime = numCorrect * 10;
        }

        // Ekranda kullanıcının doğru sayısını ve skorunu gösterir
        score.append(numCorrect+' doğrunuz var. Puanınız : '+scoreWithTime);

        scoreDB = scoreWithTime;


        return score;
    }



    // Başlat butonuna tıklandığında süre başlar ve soru ekranda gözükür.
    $(document).ready(function() {
        $("#start-exam").click(function(){
            $('#timer').show();
            var fiftyMinutes = 60 * 50;
            display = document.querySelector('#timer');
            startTimer(fiftyMinutes, display);
            $('#start-exam').hide();
            $('#finish-exam').show();
            displayNext();
        });
    });


    // Bitir butonuna tıklandı mı diye kontrol eder tıklanmışsa zamanı alır süreyi durdurur
    function checkFinishButton(){
        $(document).ready(function() {
            $("#finish-exam").click(function(){
                timer2 = document.querySelector('#timer').textContent;
                timetoDB = parseInt(timer2);
                clearTimeout(time);
                $('#next').hide();
                $('#finish-exam').hide();
                $('#prev').hide();
                $('#go-home').show();
                // Doğru sayısını ekranda gösterir
                $('#question').remove();
                var scoreElem = computeScore();
                quiz.append(scoreElem).fadeIn();



                matscore = scoreDB;

                $.ajax({
                    url: "?matscore="+matscore+"&time="+timetoDB,
                    type: "GET",
                    success: function(response)
                    {
                        console.log("sucess!");
                    },
                    error: function(e){
                        console.log("ERROR: ", e);
                    }
                });

                document.getElementById("go-home").onclick =  function()
                {
                    window.location="http://localhost:8080/exam/mat/"+id+"/?matscore="+matscore+"&time="+timetoDB;
                }

            });
        });
    }




    checkFinishButton();
    // ---------------------- TİMER ----------------------

    function startTimer(duration, display) {
        var timer = duration, minutes, seconds;
        time = setInterval(function () {
            minutes = parseInt(timer / 60, 10);  // String to int
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = minutes + ":" + seconds;

            // Süre bittiğinde
            if (--timer == -1) {
                clearTimeout(time)
                $('#next').hide();
                $('#finish-exam').hide();
                $('#prev').hide();
                $('#go-home').show();
                // Doğru sayısını ekranda gösterir
                $('#question').remove();
                var scoreElem = computeScore();
                quiz.append(scoreElem).fadeIn();

                $(document).ready(function() {
                    $("#go-home").click(function(){

                        matscore = scoreDB;

                        $.ajax({
                            url: "?matscore="+matscore+"&time="+0,
                            type: "GET",
                            success: function(response)
                            {
                                console.log("sucess!");
                            },
                            error: function(e){
                                console.log("ERROR: ", e);
                            }
                        });


                        window.location="http://localhost:8080/exam/mat/"+id+"/?matscore="+matscore+"&time="+0;


                    });
                });

            }

        }, 1000);
    }
})();
