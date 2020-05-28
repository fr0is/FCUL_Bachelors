import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginRegistoComponent } from './login-registo.component';

describe('LoginRegistoComponent', () => {
  let component: LoginRegistoComponent;
  let fixture: ComponentFixture<LoginRegistoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginRegistoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginRegistoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
