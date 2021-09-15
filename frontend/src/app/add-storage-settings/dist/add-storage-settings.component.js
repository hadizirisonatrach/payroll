"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.AddStorageSettingsComponent = void 0;
var error_dialog_component_1 = require("./../error-dialog/error-dialog.component");
var alert_dialog_component_1 = require("./../alert-dialog/alert-dialog.component");
var angular_lite_1 = require("@mobiscroll/angular-lite");
var forms_1 = require("@angular/forms");
var core_1 = require("@angular/core");
var AddStorageSettingsComponent = /** @class */ (function () {
    function AddStorageSettingsComponent(paramService, token, router, formBuilder, dialog, clotureService) {
        this.paramService = paramService;
        this.token = token;
        this.router = router;
        this.formBuilder = formBuilder;
        this.dialog = dialog;
        this.clotureService = clotureService;
        this.formGroup = new forms_1.FormGroup({});
        this.titleAlert = 'Champ obligatoire';
        this.folder = {
            "foldername": "",
            "folderpath": "",
            "idfolder": 0,
            "statusfolder": 1,
            "displayedfolderpath": ""
        };
        this.currentUser = {
            "email": "",
            "iduser": 0,
            "name": "",
            "password": "",
            "state": 0,
            "username": ""
        };
        this.folderArchive = {
            "archfoldername": "",
            "archfolderpath": "",
            "archstatusfolder": 1,
            "folderarchiveddate": new Date(),
            "idfolder": 0,
            "iduser": 0,
            "folderoperation": ""
        };
        this.fileAdded = false;
        this.archiveAdded = false;
        this.fileTypeToFolder = [];
        this.allFiles = [];
        this.tempTableFiles = [];
        this.formSettings = {
            theme: 'mobiscroll',
            themeVariant: 'light'
        };
        this.formGroup = new forms_1.FormGroup({
            foldername: new forms_1.FormControl('', [forms_1.Validators.required]),
            folderpath: new forms_1.FormControl('', [forms_1.Validators.required])
        });
    }
    AddStorageSettingsComponent.prototype.ngOnInit = function () {
        var _this = this;
        //get currentUser
        this.currentUser.username = this.token.getUsername();
        // //console.log(this.currentUser)
        this.paramService.getUserByUserName(this.currentUser).subscribe(function (data) {
            if (data != null) {
                //  //console.log(data);
                _this.currentUser.iduser = data.iduser;
            }
            else {
                _this.openDialog();
            }
        }, function (error) {
            // //console.log(error);
            _this.openDialogError(error);
            ;
            throw error;
        });
        this.paramService.getAllFileType().subscribe(function (data) {
            // //console.log(data);
            if (data != null) {
                _this.allFiles = data;
            }
            else {
                _this.openDialog();
            }
        }, function (error) {
            //  //console.log(error);
            _this.openDialogError(error);
            throw error;
        });
    };
    AddStorageSettingsComponent.prototype.openDialog = function () {
        var dialogRef = this.dialog.open(alert_dialog_component_1.AlertDialogComponent);
        dialogRef.afterClosed().subscribe(function (result) {
            window.location.reload();
        });
    };
    //check if file is checked or not
    AddStorageSettingsComponent.prototype.ischekedindex = function (file) {
        return this.tempTableFiles.indexOf(file);
    };
    //update list of checked files
    AddStorageSettingsComponent.prototype.updateCheckedFiles = function (file) {
        //console.log(file)
        if (this.tempTableFiles.length > 0) {
            var index = this.ischekedindex(file);
            if (index != -1) {
                this.tempTableFiles.splice(index, 1);
            }
            else {
                this.tempTableFiles.push(file);
            }
        }
        else {
            this.tempTableFiles.push(file);
        }
        //console.log(this.tempTableFiles)
    };
    AddStorageSettingsComponent.prototype.onSubmit = function (form) {
        var _this = this;
        if (this.tempTableFiles.length == 0) {
            this.showAlert("Ajout d'un nouveau dossier", "Vous devez sélectionner au moins un fichier à mettre dans ce dossier!");
        }
        else {
            //console.log(form)
            this.folder.foldername = form.foldername;
            this.folder.folderpath = form.folderpath;
            this.folder.displayedfolderpath = form.folderpath;
            this.paramService.addNewFolder(this.folder).subscribe(function (data) {
                //console.log(data);
                if (data != null) {
                    _this.folderArchive.archfoldername = data.foldername;
                    _this.folderArchive.archfolderpath = data.folderpath;
                    _this.folderArchive.archstatusfolder = data.statusfolder;
                    _this.folderArchive.idfolder = data.idfolder;
                    _this.folderArchive.iduser = _this.currentUser.iduser;
                    _this.folderArchive.folderoperation = "add";
                    _this.saveAddedFolderArchive(_this.folderArchive);
                }
                else {
                    _this.openDialog();
                }
            }, function (error) {
                //  //console.log(error);
                _this.openDialogError(error);
                ;
                throw error;
            });
        }
    };
    //alert pour le Files selection
    AddStorageSettingsComponent.prototype.showAlert = function (title, msg) {
        angular_lite_1.mobiscroll.alert({
            title: title,
            message: msg,
            callback: function () {
                window.location.reload();
            }
        });
    };
    AddStorageSettingsComponent.prototype.saveFilesToFolder = function (files) {
        var _this = this;
        //console.log(files)
        this.paramService.addFilesToNewFolder(files).subscribe(function (data) {
            //console.log(data);
            if (data != null) {
                _this.fileAdded = true;
                _this.showAlertAddedNewFolder();
            }
            else {
                _this.openDialog();
            }
        }, function (error) {
            //  //console.log(error);
            _this.openDialogError(error);
            ;
            throw error;
        });
    };
    AddStorageSettingsComponent.prototype.saveAddedFolderArchive = function (archiveFolder) {
        var _this = this;
        this.paramService.addNewFolderArchive(archiveFolder).subscribe(function (data) {
            //console.log(data);
            if (data != null) {
                _this.archiveAdded = true;
                for (var i = 0; i < _this.tempTableFiles.length; i++) {
                    _this.fileTypeToFolder.push({ "idfiletype": _this.tempTableFiles[i].idfiletype, "idfolder": data.idfolder });
                }
                _this.saveFilesToFolder(_this.fileTypeToFolder);
                _this.showAlertAddedNewFolder();
            }
            else {
                _this.openDialog();
            }
        }, function (error) {
            //  //console.log(error);
            _this.openDialogError(error);
            ;
            throw error;
        });
    };
    //alert pour le Files selection
    AddStorageSettingsComponent.prototype.showAlertAddedNewFolder = function () {
        var _this = this;
        if (this.archiveAdded && this.fileAdded) {
            angular_lite_1.mobiscroll.alert({
                title: "Ajout d'un nouveau dossier",
                message: "Le dossier et ses fichiers ont bien été ajoutés."
            }).then(function (result) {
                // //console.log(result ? 'Agreed.' : 'Disagreed.');
                _this.router.navigateByUrl("storageSettings");
            });
            ;
        }
    };
    AddStorageSettingsComponent.prototype.openDialogError = function (error) {
        var dialogRef = this.dialog.open(error_dialog_component_1.ErrorDialogComponent, {
            width: '650px',
            data: { message: error }
        });
        dialogRef.afterClosed().subscribe(function (result) {
            window.location.reload();
        });
    };
    AddStorageSettingsComponent = __decorate([
        core_1.Component({
            selector: 'app-add-storage-settings',
            templateUrl: './add-storage-settings.component.html',
            styleUrls: ['./add-storage-settings.component.css']
        })
    ], AddStorageSettingsComponent);
    return AddStorageSettingsComponent;
}());
exports.AddStorageSettingsComponent = AddStorageSettingsComponent;
