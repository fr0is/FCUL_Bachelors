import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopBarHotelComponent } from './top-bar-hotel.component';

describe('TopBarHotelComponent', () => {
  let component: TopBarHotelComponent;
  let fixture: ComponentFixture<TopBarHotelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopBarHotelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopBarHotelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
