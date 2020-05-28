import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartosPesquisaComponent } from './quartos-pesquisa.component';

describe('QuartosPesquisaComponent', () => {
  let component: QuartosPesquisaComponent;
  let fixture: ComponentFixture<QuartosPesquisaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartosPesquisaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartosPesquisaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
