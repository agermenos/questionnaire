/**
 * Created by agermenos on 4/14/15.
 */
function createNewDate(){
    var day = new Date();
    var strDay = (day.getMonth()+1) + "/" + (day.getDay()) + "/" + day.getFullYear();
    return strDay;
}
// Overall viewmodel for this screen, along with initial state
function AdViewModel() {
    var self = this;

    self.statuses = [
        {id:"ACTIVE", text:"active"},
        {id:"INACTIVE", text:"inactive"},
        {id:"EXPIRED", text:"expired"}
    ];

    self.brands = [
        {id:"PCWORLD", text:"PC World"},
        {id:"TECHHIVE", text:"TechHive"},
        {id:"MACWORLD", text:"Mac World"},
        {id:"INFOWORLD", text:"Info World"},
        {id:"JAVAWORLD", text:"Java World"},
        {id:"ITWORLD", text:"IT World"},
        {id:"GREENBOT", text:"Greenbot"},
        {id:"NETWORKWORLD", text:"Network World"}
    ];

    self.ads = ko.observableArray ([
        new Ad(1,"bluekai", self.brands[0], '12/1/2013', self.statuses[2], 'nolink'),
        new Ad(2,"omniture", self.brands[1], '12/12/2012', self.statuses[1], 'nolink'),
        new Ad(3,"microsoft", self.brands[2], '7/2/2010', self.statuses[0], 'nolink'),
        new Ad(4,"bluekai", self.brands[3], '10/10/2009', self.statuses[1], 'nolink'),
        new Ad(5,"bluekai", self.brands[0], createNewDate(), self.statuses[0], 'nolink')
    ]);

    self.ad = ko.observable(new Ad(0,"none", self.brands[0], createNewDate(), self.statuses[0], 'nolink'));

    self.addAd = function(){
        self.ads.push(new Ad(1,'none',self.brands[0], createNewDate(), self.statuses[0],"nolink"));
    }

    self.getLink=function(ad){
        alert("Link: " + ad.link);
    }

    self.editAd = function(ad) {
        self.ad(ad);
        $("#modalWindow").fadeIn("slow");
        $("#cancelButton").fadeIn("slow");
    };

    self.cancelEdit = function(){
        $("#modalWindow").fadeOut();
        $("#cancelButton").fadeOut();
    }
}

function Ad(id, name, brand, date, status, link){
    var self=this;
    self.id = id;
    self.name = name;
    self.date = date;
    self.status = status;
    self.brand = brand;
    self.link = link;
}

ko.applyBindings(new AdViewModel());