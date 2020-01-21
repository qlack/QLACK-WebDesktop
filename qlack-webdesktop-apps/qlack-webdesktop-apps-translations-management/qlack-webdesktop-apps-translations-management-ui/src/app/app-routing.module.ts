import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LanguageEditComponent} from './language/language-edit.component';
import {LanguageCreateComponent} from './language/language-create.component';


const routes: Routes = [
  {path: '', redirectTo: 'languages', pathMatch: 'full'},

  {path: 'languages', component: LanguageEditComponent},
  {path: 'languages/create', component: LanguageCreateComponent},
  {
    path: 'translations',
    loadChildren: () => import('./translations/translations.module').then(m => m.TranslationsModule)
  },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
