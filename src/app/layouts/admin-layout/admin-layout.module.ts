import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SatDatepickerModule, SatNativeDateModule } from 'saturn-datepicker';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { LogoutComponent } from 'app/login/logout.component';

import {
  MatButtonModule,
  MatInputModule,
  MatRippleModule,
  MatTooltipModule,
  MatSelectModule,
  MatIconModule,
  MatExpansionModule,
  MatAutocompleteModule,
  MatDialogModule,
} from '@angular/material';
import {MatTabsModule} from '@angular/material/tabs';
import {MatStepperModule} from '@angular/material/stepper';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatRadioModule} from '@angular/material/radio';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { PropertyListComponent } from 'app/property/property-list/property-list.component';
import { FileUploadModule } from "ng2-file-upload";
import { NgxGalleryModule } from 'ngx-gallery';
import { ReportComponent } from 'app/property/report/report.component';
import { VerifyComponent } from 'app/property/verify/verify.component';
import { InactiveComponent } from 'app/property/inactive/inactive.component';
import { RangesFooter } from 'app/components/ranges-footer/ranges-footer.component';
import { RegistrationsComponent } from 'app/customer/registrations/registrations.component';
import { CustomerListComponent } from 'app/customer/customer-list/customer-list.component';
import { BasicComponent } from 'app/property/forms/basic/basic.component';
import { SubmitListingComponent } from 'app/property/forms/submit-listing/submit-listing.component';
import { FloorPlanComponent } from 'app/property/forms/floor-plan/floor-plan.component';
import { PropAmenitiesComponent } from 'app/property/forms/prop-amenities/prop-amenities.component';
import { PropNearbyComponent } from 'app/property/forms/prop-nearby/prop-nearby.component';
import { PropGalleryComponent } from 'app/property/forms/prop-gallery/prop-gallery.component';
import { PropMaterialComponent } from 'app/property/forms/prop-material/prop-material.component';
import { PropBanksComponent } from 'app/property/forms/prop-banks/prop-banks.component';
import { InquiryComponent } from 'app/property/inquiry/inquiry.component';
import { InquiryReportComponent } from 'app/property/report/inquiry-report/inquiry-report.component';
import { UsersComponent } from 'app/users/users.component';
import { ConfirmPopupComponent } from 'app/popup/confirm-popup/confirm-popup.component';
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    SatDatepickerModule, 
    SatNativeDateModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatTabsModule,
    MatStepperModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatCheckboxModule,
    MatExpansionModule,
    MatIconModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatIconModule,
    NgxGalleryModule,
    FileUploadModule,
  ],
  declarations: [
    DashboardComponent,
    LogoutComponent,
    PropertyListComponent,
    ReportComponent,
    VerifyComponent,
    InactiveComponent,
    BasicComponent,
    SubmitListingComponent,
    FloorPlanComponent,
    PropAmenitiesComponent,
    PropNearbyComponent,
    PropGalleryComponent,
    PropMaterialComponent,
    PropBanksComponent,
    RangesFooter,
    RegistrationsComponent,
    CustomerListComponent,
    InquiryComponent,
    InquiryReportComponent,
    UsersComponent,
    ConfirmPopupComponent,
  ],
  entryComponents: [
    ConfirmPopupComponent
  ]
})

export class AdminLayoutModule {}
