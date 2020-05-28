import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelHomePageComponent } from './hotel-home-page.component';

describe('HotelHomePageComponent', () => {
  let component: HotelHomePageComponent;
  let fixture: ComponentFixture<HotelHomePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotelHomePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
