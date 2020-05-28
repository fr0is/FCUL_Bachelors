import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartosHomePageComponent } from './quartos-home-page.component';

describe('QuartosHomePageComponent', () => {
  let component: QuartosHomePageComponent;
  let fixture: ComponentFixture<QuartosHomePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartosHomePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartosHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
