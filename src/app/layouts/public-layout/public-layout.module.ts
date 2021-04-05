import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';
import { PublicLayoutRoutes } from './public-layout-routing';
import { PropertyInfoComponent } from 'app/property-info/property-info.component';
import { Ng5SliderModule } from 'ng5-slider';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(PublicLayoutRoutes), 
    Ng5SliderModule,
  ],
  declarations: [
    PropertyInfoComponent
  ]
})
export class PublicLayoutModule { }
