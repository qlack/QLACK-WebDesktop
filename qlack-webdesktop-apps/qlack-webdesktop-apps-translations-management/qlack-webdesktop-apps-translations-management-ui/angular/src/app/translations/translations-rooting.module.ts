import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TranslationsComponent} from './translations.component';
import {TranslationsEditComponent} from './translations-edit.component';


const routes: Routes = [
  {path: '', component: TranslationsComponent},
  {path: 'edit/:id', component: TranslationsEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TranslationsRootingModule {
}
