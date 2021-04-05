import { Routes } from '@angular/router';
import { AuthGuardService as AuthGuard } from 'app/auth/auth-guard.service';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { LogoutComponent } from 'app/login/logout.component';
import { PropertyListComponent } from 'app/property/property-list/property-list.component';
import { ReportComponent } from 'app/property/report/report.component';
import { VerifyComponent } from 'app/property/verify/verify.component';
import { InactiveComponent } from 'app/property/inactive/inactive.component';
import { RegistrationsComponent } from 'app/customer/registrations/registrations.component';
import { SubmitListingComponent } from 'app/property/forms/submit-listing/submit-listing.component';
import { InquiryComponent } from 'app/property/inquiry/inquiry.component';
import { InquiryReportComponent } from 'app/property/report/inquiry-report/inquiry-report.component';
import { UsersComponent } from 'app/users/users.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'submit-listing/:id', component: SubmitListingComponent, canActivate: [AuthGuard] },
    { path: 'view-property/:id/:type', component: SubmitListingComponent, canActivate: [AuthGuard] },
    { path: 'property-list', component: PropertyListComponent, canActivate: [AuthGuard] },
    { path: 'reports', component: ReportComponent, canActivate: [AuthGuard] },
    { path: 'reports/:name', component: ReportComponent, canActivate: [AuthGuard] },
    { path: 'reports/inquiry/:name', component: InquiryReportComponent, canActivate: [AuthGuard] },
    { path: 'verify', component: VerifyComponent, canActivate: [AuthGuard] },
    { path: 'inactive', component: InactiveComponent, canActivate: [AuthGuard] },
    { path: 'registrations', component: RegistrationsComponent, canActivate: [AuthGuard] },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    { path: 'inquiry', component: InquiryComponent, canActivate: [AuthGuard] },
    { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard] }    
];
