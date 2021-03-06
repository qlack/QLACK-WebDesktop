import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {QFormsService} from '@eurodyn/forms';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {FileService} from '../services/file.service';
import {FileDto} from '../dto/file-dto';
import {UtilityService} from "../services/utility.service";
import {QngPubsubService} from '@qlack/qng-pubsub';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-fileupload',
  templateUrl: './fileupload.component.html'
})
export class FileuploadComponent implements OnInit {
  previewImageURL: string;
  imageURL: string;
  uploadForm: FormGroup;
  files: FileDto[];
  fileId: string;
  errorMessage: string;
  successMessage: string;
  dismissMessage: string;

  constructor(public fb: FormBuilder, private fileService: FileService,
              private route: ActivatedRoute,
              private qForms: QFormsService, private router: Router, private dialog: MatDialog,
              private utilityService: UtilityService, private dialogRef: MatDialogRef<FileuploadComponent>,
              private qPubSubService: QngPubsubService, private translate: TranslateService) {
    // Reactive Form
    this.uploadForm = this.fb.group({
      file: [null]
    })
  }

  ngOnInit(): void {
    // this.fetchUploadedImages();

    this.translate.get([
      'translations-management-ui.dismiss',
      'translations-management-ui.success',
      'translations-management-ui.error'
    ])
    .subscribe(translation => {
      this.errorMessage = translation['translations-management-ui.error'];
      this.successMessage = translation['translations-management-ui.success'];
      this.dismissMessage = translation['translations-management-ui.dismiss'];
    });
  }

  changeListener(event) {
    const file = (event.target as HTMLInputElement).files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.previewImageURL = reader.result as string;
    };
    reader.readAsDataURL(file);

    this.uploadForm.patchValue({
      file: file
    });
    this.uploadForm.get('file').updateValueAndValidity()
  }

  // Submit Form
  submit() {
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('file').value);

    this.fileService.upload(formData).subscribe(onNext => {
      this.utilityService.popupSuccess("File Uploaded");
      this.previewImageURL = null;
      this.dialogRef.close();
      this.utilityService.popupSuccessAction(this.successMessage, this.dismissMessage);
      this.qPubSubService.publish('QRefreshPage', '');
      this.router.navigate(["/"]);
    }, onError => {
      this.utilityService.popupError(onError.error.message);
    });
  }

  changeImage(value: any) {
    this.fileId = value;
    this.imageURL = this.fileService.getImage(value);
  }

  close() {
    this.dialogRef.close();
  }

  private fetchUploadedImages() {
    this.fileService.getAllSorted("sort=name").subscribe(value => {
      this.files = value;
      if (this.files) {
        this.fileId = this.files[0].id.toString();
        this.changeImage(this.fileId);
      }
    }, onError => {
      this.utilityService.popupError(onError.error.message);
    });
  }
}
