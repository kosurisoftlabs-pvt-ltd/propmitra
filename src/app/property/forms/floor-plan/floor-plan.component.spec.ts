import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FloorPlanComponent } from './floor-plan.component';

describe('FloorPlanComponent', () => {
  let component: FloorPlanComponent;
  let fixture: ComponentFixture<FloorPlanComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FloorPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FloorPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
