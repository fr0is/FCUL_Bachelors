import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteDadosUpdateComponent } from './cliente-dados-update.component';

describe('ClienteDadosUpdateComponent', () => {
  let component: ClienteDadosUpdateComponent;
  let fixture: ComponentFixture<ClienteDadosUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClienteDadosUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteDadosUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
