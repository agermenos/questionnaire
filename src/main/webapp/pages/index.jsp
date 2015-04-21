<html>
<head>
    <title>Questionnaire Manager</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <style type="text/css">
        /*       * Style tweaks       * --------------------------------------------------       */
        body {
            padding-top: 70px;
        }
        footer {
            padding-left: 15px;
            padding-right: 15px;
        }
        /*       * Off Canvas       * --------------------------------------------------       */
        @media screen and(max-width: 768px) {
            .row-offcanvas {
                position: relative;
                -webkit-transition: all 0.25s ease-out;
                -moz-transition: all 0.25s ease-out;
                transition: all 0.25s ease-out;
            }
            .row-offcanvas-right .sidebar-offcanvas {
                right: -50%;
                /* 6 columns */
            }
            .row-offcanvas-left .sidebar-offcanvas {
                left: -50%;
                /* 6 columns */
            }
            .row-offcanvas-right.active {
                right: 50%;
                /* 6 columns */
            }
            .row-offcanvas-left.active {
                left: 50%;
                /* 6 columns */
            }
            .sidebar-offcanvas {
                position: absolute;
                top: 0;
                width: 50%;
                /* 6 columns */
            }
        }
    </style>
</head>
<body>
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Questionnaire Manager</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="#">Home</a>
                </li>
                <li>
                    <a href="#about">About</a>
                </li>
                <li>
                    <a href="#contact">Contact</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#login">Log In</a></li>
                <li><a href="#logout">User</a></li>
                <li><a href="#logout">User</a></li>
                <li><a href="#logout">User</a></li>
            </ul>
        </div>
        <!-- /.nav-collapse -->
    </div>
    <!-- /.container -->
</div>
<!-- /.navbar -->
<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-9">
            <p class="pull-right visible-xs">
                <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button></p>
            <div class="jumbotron" style="display: none">
                <h1>Questionnaire</h1>
                <p>This is the manager's main page. Here you can add questionnaires, or change their status. Also, you can add or remove questions for each one of them. Questionnaires that are in OPERATIONAL status can't be changed, though.</p>
            </div>
            <div class="well">

                <div class="panel panel-default">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date Created</th>
                            <th>Status</th>
                            <th>Edit</th>
                        </tr>
                        </thead>
                        <tbody data-bind="foreach: questionnaires">
                        <tr>
                            <td hidden data-bind="text: id"/>
                            </td>
                            <td data-bind="text: name"></td>
                            <td data-bind="text: date"></td>
                            <td><select data-bind="options: $root.statuses, value: status, optionsText: 'text'"></select></td>
                            <td>
                                <button id="editBtn" type="button" data-bind="click: $root.editQuestionnaire" class="btn btn-primary">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> edit
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <input class="btn btn-default" type="button" id="addQuestionnaireButton" value="Add Questionnaire"
                           data-bind="click: addQuestionnaire"/>
                </div>

            </div>
            <div id="modalWindow" class="well" style="display:none">
                <div class="panel panel-body">
                    <div class="panel-heading">Edit Questionnaire</div>
                    <div class="form-horizontal">
                        <fieldset>
                            <!-- Form Name -->
                            <legend><span id="questionnaire_name" data-bind="text: questionnaire().name"></span></legend>
                            <input id="editable_questionnaire_name" type="text" class="form-control" data-bind="value: questionnaire().name" aria-label="..." style="display: none">

                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label" for="questionnaireName" data-bind="value:questionnaire().name"> </label>

                                <div class="controls">
                                    <label class="control-label" for="questionnaireName">Questions: </label>
                                </div>
                                <div class="panel panel-default" data-bind="foreach: questions()">
                                    <div class="input-group">
                                            <span class="input-group-btn" data-bind="if:answers()">
                                                    <button data-bind="enable:answers().length>0, attr:{id:'bind_' + idx}" class="btn btn-default" type="button">Answers <span class="badge" data-bind="text:answers().length, visible:answers().length>0"></span>
                                                    </button>
                                            </span>
                                        <input type="text" class="form-control" data-bind="value:question" aria-label="...">
                                            <span class="input-group-btn">
                                                <span data-bind="if: type=='MULTIPLE_CHOICE'"> <button type="button" data-bind="click: $root.addMOAnswer" class="btn btn-default">Add Answer</button></span>
                                                <span data-bind="if: type=='SINGLE_CHOICE'"> <button type="button" data-bind="click: $root.addSOAnswer" class="btn btn-default">Add Answer</button> </span>
                                                <!-- <button type="button" class="btn btn-default dropdown-toggle" data-bind="visible:answers().length>0" data-toggle="dropdown" aria-expanded="false">Add Answer <span class="caret"></span></button>
                                                <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                                    <li data-bind="click: $root.addSOAnswer"><a role="menuitem" tabindex="-1" href="#"><span
                                                            class="glyphicon glyphicon-record"></span> Single Option Answer</a></li>
                                                    <li data-bind="click: $root.addMOAnswer"><a role="menuitem" tabindex="-1" href="#"><span
                                                            class="glyphicon glyphicon-check"></span> Multiple Option Answer</a></li>
                                                </ul> -->
                                                <button type="button" class="btn btn-default" aria-expanded="false" data-bind="click: $root.removeQuestion">Remove</button>
                                            </span>
                                    </div>
                                    <div class="input-group">
                                        <div class="panel-collapse collapse" data-bind="attr: { id: idx }" >
                                            <div  data-bind="foreach: answers()">
                                                <div class="input-group">
                                                       <span class="input-group-addon" data-bind="visible:type=='SINGLE_CHOICE'">
                                                            <input type="radio" aria-label="...">
                                                       </span>
                                                       <span class="input-group-addon" data-bind="visible:type=='MULTIPLE_CHOICE'">
                                                            <input type="checkbox" aria-label="...">
                                                       </span>
                                                    <input type="text" class="form-control" data-bind="value:question" aria-label="...">
                                                    <div class="input-group-btn">
                                                        <button type="button" class="btn btn-default" aria-expanded="false" data-bind="click: $root.removeAnswer.bind($data, $parent)">Remove</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                    Add Question <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu">
                                    <li data-bind="click: $root.addSOQuestion"><a role="menuitem" tabindex="-1" href="#"><span
                                            class="glyphicon glyphicon-record"></span> Single Option Question</a></li>
                                    <li data-bind="click: $root.addMOQuestion"><a role="menuitem" tabindex="-1" href="#"><span
                                            class="glyphicon glyphicon-check" ></span> Multiple Option Question</a></li>
                                    <li class="divider"></li>
                                    <li data-bind="click: $root.addTxtQuestion"><a role="menuitem" tabindex="-1" href="#"><span></span> Text Question</a></li>
                                </ul>
                            </div>
                        </fieldset>
                    </div>
                </div>


                <input class="btn btn-default" type="button" id="cancelQuestionnaireButton" value="Cancel"
                       data-bind="click: cancelQuestionnaire" style="display:none"/>
            </div>
        </div>
        <!--/span-->
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
            <div class="well sidebar-nav">
                <ul class="nav">
                    <li>Reports</li>
                    <li class="active">
                        <a href="#">By Report</a>
                    </li>
                    <li>
                        <a href="#">Analytics</a>
                    </li>
                    <li>
                        <a href="#">On the Account</a>
                    </li>
                    <li>My Account</li>
                    <li>
                        <a href="#">Link</a>
                    </li>
                    <li>
                        <a href="#">Link</a>
                    </li>
                    <li>
                        <a href="#">Link</a>
                    </li>
                    <li>Security</li>
                    <li>
                        <a href="#">Administrators</a>
                    </li>
                    <li>
                        <a href="#">Users</a>
                    </li>
                </ul>
            </div>
            <!--/.well -->
        </div>
    </div>
</div>
</body>
</html>


<script type="text/javascript" src="../js/jquery-2.1.3.min.js"></script>
<script type='text/javascript' src='../js/knockout-3.3.0.js'></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/ajaxScripts.js"></script>
<script type='text/javascript' src='../js/questionnaire_admin.js'></script>