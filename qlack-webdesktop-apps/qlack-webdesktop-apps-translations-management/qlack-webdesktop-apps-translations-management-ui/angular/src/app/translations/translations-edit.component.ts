import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslationService} from '../services/translation.service';
import {KeyDto} from '../dto/key-dto';
import {LanguageDto} from '../dto/language-dto';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-translations-edit',
  templateUrl: './translations-edit.component.html',
  styleUrls: ['./translations-edit.component.scss']
})
export class TranslationsEditComponent implements OnInit {

  private key: KeyDto;
  private languages: LanguageDto[] = [];

  @ViewChild('form', {static: true}) form: any;

  constructor(private translationService: TranslationService, private _snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.key = JSON.parse(localStorage.getItem('key'));
    this.languages = JSON.parse(localStorage.getItem('languages'));
  }

  save(key: KeyDto) {
    this.translationService.updateTranslationsForKey(key).subscribe(success => {
      this._snackBar.open(localStorage.getItem('changesTranslated'), localStorage.getItem('savedTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    }, Error => {
      this._snackBar.open(localStorage.getItem('errorTranslated'), localStorage.getItem('errorMessageTranslated'), {
        duration: 2000, verticalPosition: 'top'
      });
    });
    this.form.control.markAsPristine();
  }
}
