import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from './components/components.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AuthGuardService as AuthGuard } from 'app/auth/auth-guard.service';

import { AppComponent } from './app.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { AuthInterceptor } from './auth/auth-interceptor';
import { RegisterComponent } from './register/register.component';
import { PublicLayoutComponent } from './layouts/public-layout/public-layout.component';
import { LoginModule } from './login/login.module';
import { AgmCoreModule } from '@agm/core';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    NgxSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
	  TabsModule.forRoot(),
    LoginModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBE49eJ-hTzLNA7IKZ2DOnW-4BBHDzDXlA',
      libraries: ["places"],
      region: "in"
    }),
  ],
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    RegisterComponent,
    PublicLayoutComponent,
  ],
  providers: [
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
