import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ErrorComponent} from './error/error.component';
import {HeaderComponent} from './common/header/header.component';


const routes: Routes = [

  {path: 'error', component: ErrorComponent},
  {path: '', component: HeaderComponent},
  {path: '**', redirectTo: 'error'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
