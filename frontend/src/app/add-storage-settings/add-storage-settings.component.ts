import { Efile } from './../Models/Efile';
import { ErrorDialogComponent } from './../error-dialog/error-dialog.component';
import { FileTypeToFolder } from './../Models/FileTypeToFolder';
import { FolderArchive } from './../Models/FolderArchive';
import { Folder } from './../Models/Folder';
import { CloturePaieService } from './../Services/cloture-paie.service';
import { clotureFiles } from './../Models/cloturesFiles';
import { AlertDialogComponent } from './../alert-dialog/alert-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TokenStorageService } from './../auth/token-storage.service';
import { ParametreService } from './../Services/parametre.service';
import { MbscFormOptions,mobiscroll } from '@mobiscroll/angular-lite';
import { User } from './../Models/User';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { FileType } from '../Models/FileType';

@Component({
  selector: 'app-add-storage-settings',
  templateUrl: './add-storage-settings.component.html',
  styleUrls: ['./add-storage-settings.component.css']
})
export class AddStorageSettingsComponent implements OnInit {

  formGroup: FormGroup = new FormGroup({});
  titleAlert: string = 'Champ obligatoire';
 
  folder:Folder={
    "foldername":"",
    "folderpath":"",
    "idfolder":0,
    "statusfolder":1,
    "displayedfolderpath":""
  }
  currentUser: User = {
    "email": "",
    "iduser": 0,
    "name": "",
    "password": "",
    "state": 0,
    "username": ""

  }
  folderArchive: FolderArchive = {
    "archfoldername": "",
    "archfolderpath": "",
    "archstatusfolder": 1,
    "folderarchiveddate": new Date(),
    "idfolder": 0,
    "iduser": 0,
    "folderoperation":""
  }
  fileAdded:Boolean=false;
  archiveAdded:Boolean=false;
  fileTypeToFolder:FileTypeToFolder[]=[];
  allFiles:FileType[]=[];

  tempTableFiles:clotureFiles[]=[];

  formSettings: MbscFormOptions= {
    theme: 'mobiscroll',
    themeVariant: 'light'
  };
  constructor(private paramService: ParametreService, private token: TokenStorageService, private router: Router,  private formBuilder: FormBuilder
    ,public dialog: MatDialog,
    private clotureService: CloturePaieService) {

    this.formGroup = new FormGroup({
      foldername: new FormControl('', [Validators.required]),
      folderpath: new FormControl('', [Validators.required])

    });

  }

  ngOnInit(): void {
     //get currentUser
     this.currentUser.username = this.token.getUsername();
     // //console.log(this.currentUser)
     this.paramService.getUserByUserName(this.currentUser).subscribe(
       data => {
         if (data != null) {
 
           //  //console.log(data);
           this.currentUser.iduser = data.iduser;
         } else {
          this.openDialog();
         }
       },
       error => {
         // //console.log(error);
          this.openDialogError(error);;
         throw error;
       }
 
 
     );

      this.paramService.getAllFileType().subscribe(
        (data) => {

          // //console.log(data);
          if (data != null) {
            this.allFiles=data;
          } else {
            this.openDialog();
          }
  
  
  
        },
        error => {
          //  //console.log(error);
           this.openDialogError(error);
          throw error;
  
        }
  
      );

    

  }
  openDialog() {
    const dialogRef = this.dialog.open(AlertDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

   //check if file is checked or not
   ischekedindex(file:clotureFiles){
   
    return this.tempTableFiles.indexOf(file);
  
  
}
   //update list of checked files
   updateCheckedFiles(file:clotureFiles){
     //console.log(file)
    if(this.tempTableFiles.length>0){
      let index=this.ischekedindex(file);
      if(index!=-1){
        this.tempTableFiles.splice(index,1);
      }else{
        this.tempTableFiles.push(file);
      }
    }else{
      this.tempTableFiles.push(file);
    }
    
     //console.log(this.tempTableFiles)
  }

  
  onSubmit(form:any){
    if(this.tempTableFiles.length==0){
      this.showAlert("Ajout d'un nouveau dossier","Vous devez sélectionner au moins un fichier à mettre dans ce dossier!")
     }else{
        //console.log(form)
       this.folder.foldername=form.foldername;
       this.folder.folderpath=form.folderpath;
      this.folder.displayedfolderpath=form.folderpath;
       
       this.paramService.addNewFolder(this.folder).subscribe(
        (data) => {

           //console.log(data);
          if (data != null) {
            this.folderArchive.archfoldername = data.foldername;
       this.folderArchive.archfolderpath = data.folderpath;
       this.folderArchive.archstatusfolder = data.statusfolder;
       this.folderArchive.idfolder=data.idfolder;
       this.folderArchive.iduser = this.currentUser.iduser;
       this.folderArchive.folderoperation="add";
            this.saveAddedFolderArchive(this.folderArchive);
            
          } else {
            this.openDialog();
          }
  
  
  
        },
        error => {
          //  //console.log(error);
           this.openDialogError(error);;
          throw error;
  
        }
  
       );
       
       
     }
  }
  //alert pour le Files selection
  showAlert(title:String,msg:String) {
    mobiscroll.alert({
      title: title,
      message: msg
      ,callback: function () {
        window.location.reload();
       }
    });
  
 
}
saveFilesToFolder(files:Array<FileTypeToFolder>){
   //console.log(files)
  this.paramService.addFilesToNewFolder(files).subscribe(
    (data) => {

       //console.log(data);
      if (data != null) {
        
        this.fileAdded=true;
        this.showAlertAddedNewFolder();
      } else {
        this.openDialog();
      }



    },
    error => {
      //  //console.log(error);
       this.openDialogError(error);;
      throw error;

    }

   )
}
saveAddedFolderArchive(archiveFolder:FolderArchive){
  this.paramService.addNewFolderArchive(archiveFolder).subscribe(
    (data) => {

       //console.log(data);
      if (data != null) {
        this.archiveAdded=true;
        for(let i=0;i<this.tempTableFiles.length;i++){
          this.fileTypeToFolder.push({"idfiletype":this.tempTableFiles[i].idfiletype,"idfolder":data.idfolder});
          
        }
        this.saveFilesToFolder(this.fileTypeToFolder);
        this.showAlertAddedNewFolder();
      } else {
        this.openDialog();
      }



    },
    error => {
      //  //console.log(error);
       this.openDialogError(error);;
      throw error;

    }

  )
}
  //alert pour le Files selection
  showAlertAddedNewFolder() {
    if(this.archiveAdded&&this.fileAdded){
      mobiscroll.alert({
        title: "Ajout d'un nouveau dossier",
        message: "Le dossier et ses fichiers ont bien été ajoutés."
      }).then( (result) => {
        // //console.log(result ? 'Agreed.' : 'Disagreed.');
       
         this.router.navigateByUrl("storageSettings");
        
      }); ;
    }
   
  
 
}
openDialogError(error:String): void {
  const dialogRef = this.dialog.open(ErrorDialogComponent, {
    width: '650px',
    data: {message: error}
  });

  dialogRef.afterClosed().subscribe(result => {
    window.location.reload();
  });
}
}
