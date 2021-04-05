import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PropAmenitiesComponent } from './prop-amenities.component';

describe('PropAmenitiesComponent', () => {
  let component: PropAmenitiesComponent;
  let fixture: ComponentFixture<PropAmenitiesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PropAmenitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropAmenitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
