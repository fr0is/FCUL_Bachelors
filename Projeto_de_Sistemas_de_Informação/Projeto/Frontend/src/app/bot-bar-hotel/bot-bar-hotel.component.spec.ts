import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BotBarHotelComponent } from './bot-bar-hotel.component';

describe('BotBarHotelComponent', () => {
  let component: BotBarHotelComponent;
  let fixture: ComponentFixture<BotBarHotelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BotBarHotelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BotBarHotelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
