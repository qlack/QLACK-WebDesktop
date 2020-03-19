import {Component, OnInit, ViewChild} from '@angular/core';
import {TranslationService} from '../services/translation.service';
import {KeyDto} from '../dto/key-dto';
import {LanguageDto} from '../dto/language-dto';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UtilityService} from '../services/utility.service';

@Component({
  selector: 'app-translations-edit',
  templateUrl: './translations-edit.component.html',
  styleUrls: ['./translations-edit.component.scss']
})
export class TranslationsEditComponent implements OnInit {

  @ViewChild('form', {static: true}) form: any;
  private key: KeyDto;
  private languages: LanguageDto[] = [];

  constructor(private translationService: TranslationService, private _snackBar: MatSnackBar, private utilityService: UtilityService) {
  }

  ngOnInit() {
    this.key = JSON.parse(localStorage.getItem('key'));
    this.languages = JSON.parse(localStorage.getItem('languages'));
  }

  save(key: KeyDto) {
    this.translationService.updateTranslationsForKey(key).subscribe(success => {
      this.utilityService.popupSuccess(localStorage.getItem('saved'));

    }, Error => {
      this.utilityService.popupError(localStorage.getItem('error'));
    });
    this.form.control.markAsPristine();
  }
}
