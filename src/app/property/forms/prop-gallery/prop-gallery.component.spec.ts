import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PropGalleryComponent } from './prop-gallery.component';

describe('PropGalleryComponent', () => {
  let component: PropGalleryComponent;
  let fixture: ComponentFixture<PropGalleryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PropGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
