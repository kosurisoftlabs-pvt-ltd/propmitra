import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PropMaterialComponent } from './prop-material.component';

describe('PropMaterialComponent', () => {
  let component: PropMaterialComponent;
  let fixture: ComponentFixture<PropMaterialComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PropMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
