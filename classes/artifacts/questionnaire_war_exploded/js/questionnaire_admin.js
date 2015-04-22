/**
 * Questionnaire Administrator Form
 * Adds, removes and updates questionnaires
 * Developed by Alejandro Germenos
 * Dublin, CA. Mar 21, 2015
 */

function createNewDate(){
    var day = new Date();
    var strDay = (day.getMonth()+1) + "/" + (day.getDate()) + "/" + day.getFullYear();
    return strDay;
}

function QuestionnaireViewModel() {
    var self = this;

    self.id = 0;

    self.nextId = function() {
        if (!self.id) {
            self.id = 0;
        }
        return (++self.id);
    };

    self.user= {
        name:"Alejandro",
        lastName:"Germenos",
        email:"agermenos@gmail.com"
    };

    self.statuses = [
        {id:"NEW", text:"new"},
        {id:"EDITING", text:"editing"},
        {id:"OPERATIONAL", text:"operational"},
        {id:"DISABLED", text:"disabled"}
    ];

    self.questionnaire=ko.observable(new Questionnaire(0,"new questionnaire", null, self.statuses[0]));

    self.questionnaires= ko.observableArray([
        {id:1, name:"My test Questionnaire", date:"3/21/2015", status:self.statuses[0]},
        {id:2, name:"Cities of Mexico", date:"3/21/2015", status:self.statuses[1]},
        {id:3, name:"Cities of California", date:"3/21/2015", status:self.statuses[2]},
        {id:4, name:"Age Test", date:"3/21/2015", status:self.statuses[3]},
        {id:5, name:"Fitness Test", date:"3/21/2015", status:self.statuses[0]}
    ]);

    self.questions = ko.observableArray (
        [new Question(50, null, "How many fingers are in one hand?", "TEXT", ko.observableArray([]), self.nextId()),
            new Question(51, null, "What\u0027s the capital of Mexico?", "SINGLE_CHOICE", ko.observableArray([
                new Question(54, 51, "Guadalajara","SINGLE_CHOICE", [],0),
                new Question(55, 51,"Mexico City","SINGLE_CHOICE", [],0),
                new Question(56, 51,"Santa Lucía","SINGLE_CHOICE", [],0),
                new Question(57, 51,"Estroncia","SINGLE_CHOICE", [],0)]), self.nextId())
        ]
    )

    // Operations
    self.addQuestionnaire = function() {
        self.questionnaires.push(new Questionnaire(0, "new questionnaire", null, self.statuses[0]));
    };
    self.removeQuestionnaire = function(questionnaire) { self.questionnaires.remove(questionnaire) };

    self.editQuestionnaire = function(questionnaire) {
        self.questionnaire(questionnaire);
        $("#modalWindow").fadeIn("slow");
        $("#cancelQuestionnaireButton").fadeIn("slow");
        AJAX_LIB.callAJAX('http://localhost:8080/services/questions/1500', 'GET', null,  proceedEdit);

    };

    self.updateMap = function(){
        self.questions().map(function(obj){
            obj.idx;
            var button = document.getElementById('bind_'+obj.idx);

            click_bind = (function(){
                return $('#'+obj.idx).toggle();
            });
            button.onclick = click_bind;
        });
    }

    self.addTxtQuestion= function (){
        self.questions.push(new Question( 0, null, "New Text Question", "TEXT", ko.observableArray([]), self.nextId()));
        self.updateMap();
    }

    self.addMOQuestion= function (){
        self.questions.push(new Question(0, null, "New Multiple Choice Question", "MULTIPLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "MULTIPLE_CHOICE", [], 0)]), self.nextId()));
        self.updateMap();
    }

    self.addSOQuestion= function (){
        self.questions.push(new Question(0, null, "New Single Choice Question", "SINGLE_CHOICE", ko.observableArray([new Question(0,0,"Dummy", "SINGLE_CHOICE", [], 0)]), self.nextId()));
        self.updateMap();
    }

    self.removeQuestion = function(question){
        self.questions.remove(question);
        self.updateMap();
    }

    self.addSOAnswer = function(question){
        question.answers.push(new Question(0, question.id, "new answer", "SINGLE_CHOICE", ko.observableArray([]), 0));
    }

    self.addMOAnswer = function(question){

        question.answers.push(new Question(0, question.id, "new answer", "MULTIPLE_CHOICE", ko.observableArray([]), 0));

    }

    self.cancelQuestionnaire=function(){
        $("#modalWindow").fadeOut();
        $("#cancelQuestionnaireButton").fadeOut();
    };

    self.removeAnswer=function(question, answer){
        question.answers.remove(answer);
    };
}

function proceedEdit(data) {
    currentViewModel.updateMap();
    $('#questionnaire_name').click(function() {
        $('#questionnaire_name').css('display', 'none');

        $('#editable_questionnaire_name')
            .val($('#questionnaire_name').text())
            .css('display', '')
            .focus();
    });

    $('#editable_questionnaire_name').blur(function() {
        $('#editable_questionnaire_name').css('display', 'none');
        $('#questionnaire_name')
            .text($('#editable_questionnaire_name').val())
            .css('display', '');
    });
}

function Question(id, parent, question, type, answers, idx){
    var self=this;
    self.id = id;
    self.question = question;
    self.type = type;
    self.parent = parent;
    self.answers =answers;
    self.idx = idx;
}

// Class to represent a row in the seat reservations grid
function Questionnaire(id, name, date, status) {
    var self = this;
    self.id = id;
    self.name = name;
    self.date = date?date:createNewDate();
    self.status = ko.observable(status);
}

var currentViewModel =new QuestionnaireViewModel();
ko.applyBindings(currentViewModel);